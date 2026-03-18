package com.vcs.bmp.microservices.cartoperation.service;

import com.broadleafcommerce.cart.client.domain.CartItem;
import com.broadleafcommerce.cartoperation.domain.CatalogItem;
import com.broadleafcommerce.cartoperation.domain.DefaultCheckoutFailureTypes;
import com.broadleafcommerce.cartoperation.domain.checkout.CheckoutProcessDto;
import com.broadleafcommerce.cartoperation.exception.CheckoutWorkflowActivityException;
import com.broadleafcommerce.cartoperation.service.checkout.workflow.activity.CartItemValidationActivity;
import com.broadleafcommerce.cartoperation.service.configuration.CartItemConfigurationService;
import com.broadleafcommerce.cartoperation.service.provider.CatalogProvider;
import com.broadleafcommerce.data.tracking.core.context.ContextInfo;
import com.vcs.bmp.microservices.cartoperation.domain.dto.ReadCustomerOrdersCountWithSpecifiedItemRequest;
import com.vcs.bmp.microservices.cartoperation.service.provider.CustomOrderProvider;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.lang.Nullable;

import java.util.stream.Collectors;

@Slf4j
public class ConflictingOrdersValidationWorkflow extends CartItemValidationActivity {

    private final CustomOrderProvider customOrderProvider;

    public ConflictingOrdersValidationWorkflow(CatalogProvider<? extends CatalogItem> catalogProvider,
                                               CartItemConfigurationService<? extends CatalogItem>
                                                       cartItemConfigurationService,
                                               MessageSource messageSource, @NonNull CustomOrderProvider customOrderProvider) {
        super(catalogProvider, cartItemConfigurationService, messageSource);
        this.customOrderProvider = customOrderProvider;
    }

    @Override
    public CheckoutProcessDto rollback(@NonNull CheckoutProcessDto processDto, ContextInfo contextInfo) {

        return super.rollback(processDto, contextInfo);
    }

    @Override
    public CheckoutProcessDto execute(@NonNull CheckoutProcessDto processDto, @Nullable ContextInfo contextInfo) {
        CheckoutProcessDto checkoutProcessDto = super.rollback(processDto, contextInfo);

        if (processDto.getCustomerRef() == null) {
            log.info("no validation required on previous orders for non users");
            return processDto;
        }

        long repeatedItemPurchaseCount = this.customOrderProvider.readCustomerOrderCountForProductSku(
                ReadCustomerOrdersCountWithSpecifiedItemRequest.builder()
                        .customerId(processDto.getCustomerRef().getCustomerId())
                        .accountId(processDto.getCustomerRef().getAccountId())
                        .itemsSkus(processDto.getCart().getCartItems().stream().map(CartItem::getSku)
                                .collect(Collectors.toList()))
                        .build(), contextInfo
        );

        if (repeatedItemPurchaseCount > 0 ){
            throw new CheckoutWorkflowActivityException
                    (DefaultCheckoutFailureTypes.INVALID_CART_ITEM_CONFIG.name(),
                            DefaultCheckoutFailureTypes.INVALID_CART_ITEM_CONFIG.getMessagePath(),
                            checkoutProcessDto);
        }
        return processDto;
    }
}

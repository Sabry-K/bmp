package com.vcs.bmp.microservices.cartoperation.service;

import com.broadleafcommerce.cartoperation.domain.CatalogItem;
import com.broadleafcommerce.cartoperation.domain.CheckoutFailureType;
import com.broadleafcommerce.cartoperation.domain.DefaultCheckoutFailureTypes;
import com.broadleafcommerce.cartoperation.domain.checkout.CheckoutProcessDto;
import com.broadleafcommerce.cartoperation.exception.CheckoutWorkflowActivityException;
import com.broadleafcommerce.cartoperation.service.checkout.workflow.activity.CartItemValidationActivity;
import com.broadleafcommerce.cartoperation.service.configuration.CartItemConfigurationService;
import com.broadleafcommerce.cartoperation.service.provider.CatalogProvider;
import com.broadleafcommerce.data.tracking.core.context.ContextInfo;
import lombok.NonNull;
import org.springframework.context.MessageSource;

public class ConflictingOrdersValidationWorkflow extends CartItemValidationActivity {

    public ConflictingOrdersValidationWorkflow(CatalogProvider<? extends CatalogItem> catalogProvider, CartItemConfigurationService<? extends CatalogItem> cartItemConfigurationService, MessageSource messageSource) {
        super(catalogProvider, cartItemConfigurationService, messageSource);
    }

    @Override
    public CheckoutProcessDto rollback(@NonNull CheckoutProcessDto processDto, ContextInfo contextInfo) {

        return super.rollback(processDto , contextInfo);
    }

    @Override
    public CheckoutProcessDto execute(@NonNull CheckoutProcessDto processDto, ContextInfo contextInfo) {
           CheckoutProcessDto checkoutProcessDto = super.rollback(processDto, contextInfo);
        throw new CheckoutWorkflowActivityException
                (DefaultCheckoutFailureTypes.INVALID_CART_ITEM_CONFIG.name(),
                        DefaultCheckoutFailureTypes.INVALID_CART_ITEM_CONFIG.getMessagePath(),
                        checkoutProcessDto);
    }
}

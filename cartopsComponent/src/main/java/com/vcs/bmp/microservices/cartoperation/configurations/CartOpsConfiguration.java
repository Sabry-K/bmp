package com.vcs.bmp.microservices.cartoperation.configurations;

import com.broadleafcommerce.cartoperation.domain.CatalogItem;
import com.broadleafcommerce.cartoperation.service.autoconfigure.CartOperationServiceAutoConfiguration;
import com.broadleafcommerce.cartoperation.service.checkout.workflow.activity.CheckoutWorkflowActivity;
import com.broadleafcommerce.cartoperation.service.configuration.CartItemConfigurationService;
import com.broadleafcommerce.cartoperation.service.provider.CatalogProvider;
import com.vcs.bmp.microservices.cartoperation.service.ConflictingOrdersValidationWorkflow;
import com.vcs.bmp.microservices.cartoperation.service.provider.CustomOrderProvider;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;


@Configuration
@AutoConfigureBefore(CartOperationServiceAutoConfiguration.class)
@ComponentScan(basePackages = "com.vcs.bmp.microservices.cartoperation.configurations")
public class CartOpsConfiguration {


    @Bean(name = "cartItemValidationActivity")
    @Order(7000)
    CheckoutWorkflowActivity cartItemValidationActivity(CatalogProvider<? extends CatalogItem> catalogProvider,
                                                        CartItemConfigurationService<? extends CatalogItem>
                                                                cartItemConfigurationService,
                                                        MessageSource messageSource , CustomOrderProvider customOrderProvider) {
        return new ConflictingOrdersValidationWorkflow(catalogProvider, cartItemConfigurationService, messageSource,
                customOrderProvider);
    }


}

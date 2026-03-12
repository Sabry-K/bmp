package com.vcs.bmp.microservices.cartoperation.configurations;

import com.broadleafcommerce.cartoperation.domain.CatalogItem;
import com.broadleafcommerce.cartoperation.service.checkout.workflow.activity.CheckoutWorkflowActivity;
import com.broadleafcommerce.cartoperation.service.configuration.CartItemConfigurationService;
import com.broadleafcommerce.cartoperation.service.provider.CatalogProvider;
import com.vcs.bmp.microservices.cartoperation.service.ConflictingOrdersValidationWorkflow;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class CartOpsConfiguration {


    @Bean(name = "cartItemValidationActivity")
    @Order(7000)
    CheckoutWorkflowActivity cartItemValidationActivity(CatalogProvider<? extends CatalogItem> catalogProvider,
                                                        CartItemConfigurationService<? extends CatalogItem>
                                                                cartItemConfigurationService,
                                                        MessageSource messageSource) {
        return new ConflictingOrdersValidationWorkflow(catalogProvider, cartItemConfigurationService, messageSource);
    }

}

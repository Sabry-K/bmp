package com.vcs.bmp.microservices.cartoperation.configurations;

import com.broadleafcommerce.cartoperation.domain.CatalogItem;
import com.broadleafcommerce.cartoperation.service.autoconfigure.CartOperationServiceAutoConfiguration;
import com.broadleafcommerce.cartoperation.service.autoconfigure.SSLVerificationProperties;
import com.broadleafcommerce.cartoperation.service.checkout.workflow.activity.CheckoutWorkflowActivity;
import com.broadleafcommerce.cartoperation.service.configuration.CartItemConfigurationService;
import com.broadleafcommerce.cartoperation.service.provider.CatalogProvider;
import com.broadleafcommerce.common.extension.TypeFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vcs.bmp.microservices.cartoperation.configurations.properties.CustomOrderProviderProperties;
import com.vcs.bmp.microservices.cartoperation.service.ConflictingOrdersValidationWorkflow;
import com.vcs.bmp.microservices.cartoperation.service.provider.CustomOrderProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import javax.net.ssl.SSLException;
import java.util.function.Supplier;

@Configuration
@AutoConfigureBefore(CartOperationServiceAutoConfiguration.class)
@ComponentScan(basePackages = "com.vcs.bmp.microservices.cartoperation.configurations")
public class CartOpsConfiguration {

    private CustomOrderProvider customOrderProvider;

    @Bean
    public WebClient orderWebClient(
            @Qualifier("oAuth2FilterFunctionSupplier")
            Supplier<ServletOAuth2AuthorizedClientExchangeFilterFunction> oauth2FilterSupplier,
            ObjectMapper objectMapper,
            SSLVerificationProperties sslVerificationProperties) throws SSLException {

        ExchangeStrategies strategies = ExchangeStrategies
                .builder()
                .codecs(clientDefaultCodecsConfigurer -> {
                    clientDefaultCodecsConfigurer.defaultCodecs().jackson2JsonEncoder(
                            new Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON));
                    clientDefaultCodecsConfigurer.defaultCodecs().jackson2JsonDecoder(
                            new Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON));
                    clientDefaultCodecsConfigurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024);
                }).build();

        WebClient.Builder webClientBuilder = WebClient.builder();

        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory();
        uriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

        return webClientBuilder
                .uriBuilderFactory(uriBuilderFactory)
                .exchangeStrategies(strategies)
                .apply(oauth2FilterSupplier.get().oauth2Configuration())
                .build();
    }

    @Bean
    public CustomOrderProvider customOrderProvider(
            @Qualifier("orderWebClient") WebClient orderWebClient,
            ObjectMapper mapper,
            TypeFactory typeFactory, CustomOrderProviderProperties customOrderProviderProperties) {
        this.customOrderProvider = new CustomOrderProvider(orderWebClient, mapper
                , typeFactory, customOrderProviderProperties);
        return customOrderProvider;
    }

    @Bean(name = "cartItemValidationActivity")
    @Order(1000)
    @DependsOn({"customOrderProvider" , "orderWebClient"})
    CheckoutWorkflowActivity cartItemValidationActivity(CatalogProvider<? extends CatalogItem> catalogProvider,
                                                        CartItemConfigurationService<? extends CatalogItem>
                                                                cartItemConfigurationService,
                                                        MessageSource messageSource) {
        return new ConflictingOrdersValidationWorkflow(catalogProvider, cartItemConfigurationService, messageSource,
                customOrderProvider);
    }


}

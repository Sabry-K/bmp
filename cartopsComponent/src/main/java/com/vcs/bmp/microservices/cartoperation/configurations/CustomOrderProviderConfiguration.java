package com.vcs.bmp.microservices.cartoperation.configurations;


import com.broadleafcommerce.cartoperation.service.autoconfigure.SSLVerificationProperties;
import com.broadleafcommerce.common.extension.TypeFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vcs.bmp.microservices.cartoperation.configurations.properties.CustomOrderProviderProperties;
import com.vcs.bmp.microservices.cartoperation.service.provider.CustomOrderProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@EnableConfigurationProperties(SSLVerificationProperties.class)
public class CustomOrderProviderConfiguration {

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
        return new CustomOrderProvider(orderWebClient, mapper
                , typeFactory, customOrderProviderProperties);
    }


}

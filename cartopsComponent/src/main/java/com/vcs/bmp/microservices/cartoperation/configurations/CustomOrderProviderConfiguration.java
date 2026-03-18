package com.vcs.bmp.microservices.cartoperation.configurations;


import com.broadleafcommerce.cartoperation.service.autoconfigure.SSLVerificationProperties;
import com.broadleafcommerce.common.extension.TypeFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vcs.bmp.microservices.cartoperation.configurations.properties.CustomOrderProviderProperties;
import com.vcs.bmp.microservices.cartoperation.service.provider.CustomOrderProvider;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
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
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import reactor.netty.http.client.HttpClient;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;

import javax.net.ssl.SSLException;
import java.util.function.Supplier;

@Slf4j
@Configuration
@EnableConfigurationProperties(SSLVerificationProperties.class)
public class CustomOrderProviderConfiguration {

    @Bean
    public WebClient orderWebClient(
            @Qualifier("oAuth2FilterFunctionSupplier")
            Supplier<ServletOAuth2AuthorizedClientExchangeFilterFunction> oauth2FilterSupplier,
            ObjectMapper objectMapper,
            SSLVerificationProperties sslVerificationProperties) throws SSLException {

        // Build Netty HttpClient with SSL disabled conditionally
        HttpClient httpClient = HttpClient.create();

       log.info("SSL verification is : {} " ,  sslVerificationProperties.isDisabled());

        if (sslVerificationProperties.isDisabled()) {
            log.warn("------------------ SSl verification is disabled --------------------");
            SslContext sslContext = SslContextBuilder
                    .forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE)// ← skips cert check
                    .build();
            httpClient = httpClient.secure(sslSpec -> sslSpec.sslContext(sslContext));
        }

        ReactorClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);

        ExchangeStrategies strategies = ExchangeStrategies
                .builder()
                .codecs(configurer -> {
                    configurer.defaultCodecs().jackson2JsonEncoder(
                            new Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON));
                    configurer.defaultCodecs().jackson2JsonDecoder(
                            new Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON));
                    configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024);
                }).build();

        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory();
        uriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

        return WebClient.builder()
                .clientConnector(connector)              // ← wire in Netty client
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

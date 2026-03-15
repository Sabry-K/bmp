package com.vcs.bmp.microservices.cartoperation.service.provider;


import com.broadleafcommerce.cartoperation.service.provider.external.AbstractExternalProvider;
import com.broadleafcommerce.common.extension.TypeFactory;
import com.broadleafcommerce.data.tracking.core.context.ContextInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vcs.bmp.microservices.cartoperation.configurations.properties.CustomOrderProviderProperties;
import com.vcs.bmp.microservices.cartoperation.domain.dto.ReadCustomerOrdersCountWithSpecifiedItemRequest;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@Setter
@Getter
@Component
public class CustomOrderProvider extends AbstractExternalProvider implements ICustomOrderProvider {

    private static final Logger log = LogManager.getLogger(CustomOrderProvider.class);
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final TypeFactory typeFactory;
    private final CustomOrderProviderProperties properties;


    public CustomOrderProvider(WebClient webClient, ObjectMapper objectMapper,
                               TypeFactory typeFactory, CustomOrderProviderProperties customOrderProviderProperties) {
        super(webClient, objectMapper, typeFactory);
        this.webClient = webClient;
        this.objectMapper = objectMapper;
        this.typeFactory = typeFactory;
        this.properties = customOrderProviderProperties;
    }


    @Override
    public Integer readCustomerOrderCountForProductSku(ReadCustomerOrdersCountWithSpecifiedItemRequest request,
                                                       ContextInfo contextInfo) {
        String readCustomerOrderCountUri = this.getBaseUri()
                .path(this.properties.getCountOrdersWithSpecificItemForUserUri())
                .queryParam("customerId" ,request.getCustomerId())
                .queryParam("accountId" , request.getAccountId())
                .queryParam("itemsSkus" , request.getItemsSkus())
                .toUriString();
        return  this.executeRequest(() -> ((((WebClient.RequestBodySpec)this.getWebClient().get()
                .uri(readCustomerOrderCountUri, new Object[0]))
                .headers((headers) -> headers.putAll(this.getHeaders(contextInfo))))
                .attributes(
                        ServletOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId(this.getServiceClient()
                        ))).contentType(MediaType.APPLICATION_JSON)
                .accept(new MediaType[]{MediaType.APPLICATION_JSON}).retrieve()
                .bodyToMono(new ParameterizedTypeReference<Integer>() {
        }).block());
    }

    private UriComponentsBuilder getBaseUri() {
        log.info("THE ORDER URL IS {} " , this.properties.getUrl());
        log.info("THE SERVICE CLIENT IS  {} " , this.properties.getServiceClient() );
        log.info("THE ORDER URI Context {} " , this.properties.getOrderUri() );
        log.info("THE END POINT PATH IS {} " , this.properties.getCountOrdersWithSpecificItemForUserUri() );
        return UriComponentsBuilder.fromHttpUrl(this.properties.getUrl())
                .path(this.properties.getOrderUri());

    }

    private String getServiceClient(){
        return this.properties.getServiceClient();
    }
}

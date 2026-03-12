package com.vcs.bmp.microservices.cartoperation.service.provider;


import com.broadleafcommerce.cartoperation.service.provider.external.AbstractExternalProvider;
import com.broadleafcommerce.common.extension.TypeFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.reactive.function.client.WebClient;

public class CustomOrderProvider extends AbstractExternalProvider {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final TypeFactory typeFactory;

    public CustomOrderProvider(WebClient webClient, ObjectMapper objectMapper, TypeFactory typeFactory) {
        super(webClient, objectMapper, typeFactory);
        this.webClient = webClient;
        this.objectMapper = objectMapper;
        this.typeFactory = typeFactory;
    }



}

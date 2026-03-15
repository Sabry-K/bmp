package com.vcs.bmp.microservices.cartoperation.configurations.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("broadleaf.cartoperation.orderprovider")
public class CustomOrderProviderProperties {
    private String url;
    private String orderUri;
    private String countOrdersWithSpecificItemForUserUri;
    private String serviceClient = "cartopsclient";
}

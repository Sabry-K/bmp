package com.vcs.bmp.microservices.cartoperation.configurations.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "broadleaf.cartoperation.orderprovider")
public class CustomOrderProviderProperties {
     String url;
     String orderUri;
     String countOrdersWithSpecificItemForUserUri;
     String serviceClient = "cartopsclient";
}

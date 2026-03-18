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
     private String url = "https://cart:9458/api/order";
     private String orderUri = "";
     private String countOrdersWithSpecificItemForUserUri = "/purchased-items/count";
     private String serviceClient = "cartopsclient";
}

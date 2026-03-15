package com.vcs.bmp.microservices.cartoperation.configurations;


import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore(CartOpsConfiguration.class)
public class CustomOrderProviderConfiguration {




}

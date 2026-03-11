package com.vcs.bmp.microservices.catalog.configurations;

import com.broadleafcommerce.common.jpa.autoconfigure.CommonJpaAutoConfiguration;
import com.broadleafcommerce.common.jpa.data.entity.JpaEntityScan;
import com.vcs.bmp.microservices.catalog.endpoint.PreVettedProductController;
import com.vcs.bmp.microservices.catalog.service.PreVettedProductService;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore(CommonJpaAutoConfiguration.class)
@JpaEntityScan(basePackages = {"com.vcs.bmp.microservices.catalog.domain"})
@ComponentScan(basePackageClasses = {PreVettedProductService.class, PreVettedProductController.class})
public class CatalogConfiguration {
}

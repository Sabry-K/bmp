package com.vcs.bmp.microservices.catalog;

import com.broadleafcommerce.common.jpa.autoconfigure.CommonJpaAutoConfiguration;
import com.broadleafcommerce.common.jpa.data.entity.JpaEntityScan;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore(CommonJpaAutoConfiguration.class)
@JpaEntityScan(basePackages = {"com.vcs.bmp.microservices.catalog.domain"})
public class CatalogConfiguration {
}

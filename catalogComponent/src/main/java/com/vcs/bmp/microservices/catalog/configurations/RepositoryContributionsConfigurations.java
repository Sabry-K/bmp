package com.vcs.bmp.microservices.catalog.configurations;

import com.broadleafcommerce.catalog.provider.jpa.repository.product.JpaProductRepository;
import com.broadleafcommerce.data.tracking.core.RepositoryContribution;
import com.broadleafcommerce.data.tracking.jpa.filtering.narrow.factory.DefaultJpaTrackableRepositoryDelegateHelper;
import com.broadleafcommerce.data.tracking.jpa.filtering.narrow.factory.JpaTrackableRepositoryDelegateHelper;
import com.vcs.bmp.microservices.catalog.domain.PreVettedProduct;
import com.vcs.bmp.microservices.catalog.repository.PreVettedDynamicRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class RepositoryContributionsConfigurations {

    @Bean
    public JpaTrackableRepositoryDelegateHelper<PreVettedProduct> delegateHelper() {
        return new DefaultJpaTrackableRepositoryDelegateHelper<>(PreVettedProduct.class,
                JpaProductRepository.class);
    }

    @Bean
    public RepositoryContribution contribution() {
        return new RepositoryContribution()
                .withBaseRepositoryInterface(JpaProductRepository.class)
                .withQueryFragments(Collections
                        .singletonList(PreVettedDynamicRepository.class));
    }

}

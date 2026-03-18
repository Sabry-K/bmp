package com.vcs.bmp.microservices.order.configurations;

import com.broadleafcommerce.common.jpa.autoconfigure.CommonJpaAutoConfiguration;
import com.broadleafcommerce.common.jpa.data.entity.JpaEntityScan;
import com.broadleafcommerce.data.tracking.core.RepositoryContribution;
import com.broadleafcommerce.data.tracking.jpa.filtering.narrow.factory.DefaultJpaTrackableRepositoryDelegateHelper;
import com.broadleafcommerce.data.tracking.jpa.filtering.narrow.factory.JpaTrackableRepositoryDelegateHelper;
import com.broadleafcommerce.order.provider.jpa.domain.JpaOrder;
import com.vcs.bmp.microservices.order.controller.CustomOrderEndpoint;
import com.vcs.bmp.microservices.order.repository.CustomOrderRepository;
import com.vcs.bmp.microservices.order.repository.ICustomOrderRepository;
import com.vcs.bmp.microservices.order.service.CustomOrderService;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
@AutoConfigureBefore(CommonJpaAutoConfiguration.class)
@JpaEntityScan(basePackages = {"com.vcs.bmp.microservices.order.domain"})
@ComponentScan(basePackageClasses = {CustomOrderService.class, CustomOrderEndpoint.class})
public class OrderConfigurations {

    @Bean
    public JpaTrackableRepositoryDelegateHelper<JpaOrder> delegateHelper() {
        return new DefaultJpaTrackableRepositoryDelegateHelper<>(JpaOrder.class,
                ICustomOrderRepository.class);
    }

    @Bean
    public ICustomOrderRepository fragment(
            JpaTrackableRepositoryDelegateHelper<JpaOrder> delegateHelper) {
        return new CustomOrderRepository(delegateHelper);
    }

    @Bean
    public RepositoryContribution contribution(
            ICustomOrderRepository fragment) {
        return new RepositoryContribution()
                .withBaseRepositoryInterface(JpaOrder.class)
                .withConcreteFragments(
                        Collections.singletonMap(
                                ICustomOrderRepository.class,
                                fragment));
    }

}

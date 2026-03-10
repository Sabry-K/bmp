package com.vcs.bmp.microservices.order;


import com.broadleafcommerce.common.extension.data.DataRoutes;
import com.broadleafcommerce.common.jpa.autoconfigure.CommonJpaAutoConfiguration;
import com.broadleafcommerce.common.jpa.data.entity.JpaEntityScan;
import com.broadleafcommerce.data.tracking.jpa.filtering.auto.EnableJpaTrackableFlow;
import com.vcs.bmp.microservices.order.domain.JpaSubscription;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore(CommonJpaAutoConfiguration.class)
@JpaEntityScan(basePackages = {"com.vcs.bmp.microservices.order.domain"})
@EnableJpaTrackableFlow(
        entityClass = JpaSubscription.class,
        routeKey = DataRoutes.ORDER)
public class SubscriptionConfiguration {

}

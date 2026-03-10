package com.vcs.bmp.microservices.catalog.domain;


import com.broadleafcommerce.catalog.provider.jpa.domain.product.JpaProduct;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "SUBSCRIPTION_PRODUCT")
@Inheritance(strategy = InheritanceType.JOINED)

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SubscriptionProduct extends JpaProduct {

//    @Column(name = "SUBSCRIPTION_TYPE" , nullable = true)
//    private SubscriptionType subscriptionType = null;

    @Column(name = "SUBSCRIPTION_START_DATE")
    private Timestamp subscriptionStartDate;

    @Column(name = "SUBSCRIPTION_END_DATE")
    private Timestamp subscriptionEndDate;
}

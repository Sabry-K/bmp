package com.vcs.bmp.microservices.order.domain;

import com.broadleafcommerce.common.jpa.JpaConstants;
import com.broadleafcommerce.data.tracking.core.TenantTrackable;
import com.broadleafcommerce.data.tracking.core.TrackableBehavior;
import com.broadleafcommerce.data.tracking.core.TrackableExtension;
import com.broadleafcommerce.data.tracking.core.filtering.domain.Tracking;
import com.broadleafcommerce.data.tracking.jpa.filtering.TrackingListener;
import com.broadleafcommerce.data.tracking.jpa.filtering.domain.TenantJpaTracking;
import com.broadleafcommerce.data.tracking.jpa.hibernate.UlidUserType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "SUBSCRIPTION")
@EntityListeners(TrackingListener.class)
@TrackableExtension(TrackableBehavior.TENANT)
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@ToString(callSuper = true)
public class JpaSubscription implements TenantTrackable<TenantJpaTracking> , Serializable {

    @Id
    @GeneratedValue(generator = "blcid")
    @GenericGenerator(name = "blcid", strategy = "blcid")
    @Type(UlidUserType.class)
    @Column(name = "ID", nullable = false, length = JpaConstants.CONTEXT_ID_LENGTH)
    private String contextId;

    @Embedded
    private TenantJpaTracking tracking;

    @Column(name = "CUSTOMER_REF", nullable = false)
    protected String customerRef;          // context key to Customer

    @Column(name = "PRODUCT_REF", nullable = false)
    protected String productRef;           // context key to Catalog Product

    @Column(name = "STATUS", nullable = false)
    protected String status = SubscriptionStatus.ACTIVE.name();

    @Column(name = "BILLING_PERIOD")
    protected String billingPeriod;        // e.g. MONTHLY, ANNUAL

    @Column(name = "NEXT_BILLING_DATE")
    protected Instant nextBillingDate;

    @Column(name = "PRICE_PER_PERIOD", precision = 19, scale = 4)
    protected BigDecimal pricePerPeriod;

    @Column(name = "CURRENCY_CODE", length = 3)
    protected String currencyCode;

    @Column(name = "ORDER_REF")
    protected String orderRef;             // originating order

    @CreatedDate
    @Column(name = "CREATED_AT", updatable = false)
    protected Instant createdAt;

    @LastModifiedDate
    @Column(name = "UPDATED_AT")
    protected Instant updatedAt;

}



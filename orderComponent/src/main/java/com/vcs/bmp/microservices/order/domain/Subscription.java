package com.vcs.bmp.microservices.order.domain;

import com.broadleafcommerce.data.tracking.core.Trackable;

import java.math.BigDecimal;
import java.time.Instant;

public interface Subscription extends Trackable {

    String getCustomerRef();
    void setCustomerRef(String customerRef);
    String getProductRef();
    void setProductRef(String productRef);
    SubscriptionStatus getStatus();
    void setStatus(SubscriptionStatus status);
    String getBillingPeriod();
    void setBillingPeriod(String billingPeriod);
    Instant getNextBillingDate();
    void setNextBillingDate(Instant nextBillingDate);
    BigDecimal getPricePerPeriod();

    void setPricePerPeriod(BigDecimal price);
    String getCurrencyCode();
    void setCurrencyCode(String code);
    String getOrderRef();
    void setOrderRef(String orderRef);


}

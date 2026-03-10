package com.vcs.bmp.microservices.catalog.domain;

import jakarta.persistence.Embeddable;
@Embeddable
public enum SubscriptionType {
    ANNUAL , MONTHLY , QUARTERLY , WEEKLY , CUSTOM
}

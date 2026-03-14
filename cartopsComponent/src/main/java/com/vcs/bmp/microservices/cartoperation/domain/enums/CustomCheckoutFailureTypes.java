package com.vcs.bmp.microservices.cartoperation.domain.enums;

import com.broadleafcommerce.cartoperation.domain.CheckoutFailureType;

public enum CustomCheckoutFailureTypes implements CheckoutFailureType {
    ITEM_ALREADY_PURCHASED_FAILURE
    ;

    @Override
    public String getMessagePath() {
        throw new IllegalStateException();
    }

    //TODO add custom failure types

}

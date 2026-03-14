package com.vcs.bmp.microservices.order.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReadCustomerOrdersCountWithSpecifiedItemRequest {

    private String customerId;
    private String accountId;
    private List<String> itemsSkus;

}

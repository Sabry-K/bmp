package com.vcs.bmp.microservices.cartoperation.domain.dto;

import com.broadleafcommerce.data.tracking.core.context.ContextInfo;
import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReadCustomerOrdersCountWithSpecifiedItemRequest {

    private String customerId;
    private String accountId;
    private List<String> itemsSkus;
}




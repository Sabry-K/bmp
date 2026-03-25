package com.vcs.bmp.microservices.order.controller;

import com.broadleafcommerce.common.extension.data.DataRouteByExample;
import com.broadleafcommerce.common.extension.data.DataRouteByKey;
import com.broadleafcommerce.data.tracking.core.context.ContextInfo;
import com.broadleafcommerce.order.provider.RouteConstants;
import com.broadleafcommerce.order.provider.jpa.domain.JpaOrder;
import com.vcs.bmp.microservices.order.domain.dto.ReadCustomerOrdersCountWithSpecifiedItemRequest;
import com.vcs.bmp.microservices.order.service.CustomOrderService;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.N;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
@DataRouteByKey(RouteConstants.Persistence.ORDER_ROUTE_KEY)
public class CustomOrderEndpoint {

    private final CustomOrderService customOrderService;

    @GetMapping("/purchased-items/count")
    public ResponseEntity<Long> readCustomerOrdersCountWithSpecifiedItems(
            @NonNull ReadCustomerOrdersCountWithSpecifiedItemRequest request, @Nullable ContextInfo contextInfo){

        return ResponseEntity.ok(this.customOrderService.countCustomerOrdersWithSpecifiedSkus(request , contextInfo));

    }

}

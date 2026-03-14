package com.vcs.bmp.microservices.order.controller;

import com.broadleafcommerce.data.tracking.core.context.ContextInfo;
import com.vcs.bmp.microservices.order.domain.dto.ReadCustomerOrdersCountWithSpecifiedItemRequest;
import com.vcs.bmp.microservices.order.service.CustomOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class CustomOrderEndpoint {

    private final CustomOrderService customOrderService;

    @GetMapping("/purchased-items/count")
    public ResponseEntity<Integer> readCustomerOrdersCountWithSpecifiedItems(
            @NonNull ReadCustomerOrdersCountWithSpecifiedItemRequest request, @Nullable ContextInfo contextInfo){

        return ResponseEntity.ok(this.customOrderService.countCustomerOrdersWithSpecifiedSkus(request , contextInfo));

    }

}

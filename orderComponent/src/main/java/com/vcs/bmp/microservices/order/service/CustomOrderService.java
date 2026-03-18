package com.vcs.bmp.microservices.order.service;


import com.broadleafcommerce.data.tracking.core.context.ContextInfo;
import com.broadleafcommerce.order.provider.jpa.domain.JpaOrder;
import com.broadleafcommerce.order.provider.jpa.repository.JpaOrderRepository;
import com.vcs.bmp.microservices.order.domain.dto.ReadCustomerOrdersCountWithSpecifiedItemRequest;
import com.vcs.bmp.microservices.order.repository.CustomOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomOrderService {

    private final JpaOrderRepository<JpaOrder> orderRepository;

    public Long countCustomerOrdersWithSpecifiedSkus(@NonNull ReadCustomerOrdersCountWithSpecifiedItemRequest request,
                                                        @Nullable ContextInfo contextInfo) {

        log.info("Counting Orders with Specified skus {}" , request.getItemsSkus());
        return ((CustomOrderRepository)this.orderRepository)
                .countOrdersByCustomerIdAndAccountIdAndOrderItemsSkuIn(request.getCustomerId(),
                request.getAccountId(), request.getItemsSkus(), contextInfo);
    }

}

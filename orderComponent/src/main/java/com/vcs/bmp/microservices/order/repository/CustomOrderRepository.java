package com.vcs.bmp.microservices.order.repository;

import com.broadleafcommerce.data.tracking.core.context.ContextInfo;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

public interface CustomOrderRepository {

    Long countOrdersByCustomerIdAndAccountIdAndOrderItemsSkuIn(@NonNull String customerId,
                                                             @NonNull  String accountId ,
                                                             @NonNull List<String> itemSkus,
                                                             @Nullable ContextInfo contextInfo);

}

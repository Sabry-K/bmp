package com.vcs.bmp.microservices.order.repository;

import com.broadleafcommerce.data.tracking.core.context.ContextInfo;
import org.springframework.lang.Nullable;

import java.util.List;

public interface ICustomOrderRepository {

    Long countOrdersByCustomerIdAndAccountIdAndOrderItemsSkuIn( String customerId,
                                                               String accountId ,
                                                              List<String> itemSkus,
                                                             @Nullable ContextInfo contextInfo);

}

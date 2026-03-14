package com.vcs.bmp.microservices.cartoperation.service.provider;


import com.broadleafcommerce.data.tracking.core.context.ContextInfo;
import com.vcs.bmp.microservices.cartoperation.domain.dto.ReadCustomerOrdersCountWithSpecifiedItemRequest;
import org.springframework.lang.Nullable;

public interface ICustomOrderProvider {

     Integer readCustomerOrderCountForProductSku(ReadCustomerOrdersCountWithSpecifiedItemRequest request,
                                                 @Nullable ContextInfo contextInfo);

}

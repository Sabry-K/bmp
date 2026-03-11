package com.vcs.bmp.microservices.catalog.endpoint;

import com.broadleafcommerce.catalog.domain.product.Product;
import com.broadleafcommerce.common.extension.data.DataRouteByExample;
import com.broadleafcommerce.data.tracking.core.context.ContextInfo;
import com.broadleafcommerce.data.tracking.core.context.ContextOperation;
import com.broadleafcommerce.data.tracking.core.policy.Policy;
import com.broadleafcommerce.data.tracking.core.type.OperationType;
import com.vcs.bmp.microservices.catalog.domain.PreVettedProduct;
import com.vcs.bmp.microservices.catalog.service.PreVettedProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/products"})
@RequiredArgsConstructor
@DataRouteByExample(Product.class)
public class PreVettedProductController {


    private final PreVettedProductService preVettedProductService;

    @RequestMapping(method = RequestMethod.GET ,  path = "/pre-vetted")
    @Policy(permissionRoots = {"PRODUCT"})
    public Page<PreVettedProduct> findPreVettedProduct(HttpServletRequest request,
                                                       @ContextOperation(OperationType.READ) ContextInfo context,
                                                       Pageable pageable) {

        return this.preVettedProductService.findPreVettedProducts(pageable);

    }
}



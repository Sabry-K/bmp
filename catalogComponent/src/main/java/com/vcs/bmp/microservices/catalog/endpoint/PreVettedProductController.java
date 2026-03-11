package com.vcs.bmp.microservices.catalog.endpoint;

import com.vcs.bmp.microservices.catalog.domain.PreVettedProduct;
import com.vcs.bmp.microservices.catalog.service.PreVettedProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/catalog/products"})
@RequiredArgsConstructor
public class PreVettedProductController {


    private final PreVettedProductService preVettedProductService;

    @GetMapping("/pre-vetted")
    public Page<PreVettedProduct> findPreVettedProduct(Pageable pageable) {

        return this.preVettedProductService.findPreVettedProducts(pageable);

    }
}



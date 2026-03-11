package com.vcs.bmp.microservices.catalog.endpoint;

import com.vcs.bmp.microservices.catalog.domain.PreVettedProduct;
import com.vcs.bmp.microservices.catalog.service.PreVettedProductService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/catalog/products"})
@RequiredArgsConstructor
public class PreVettedProductController {


    private static final Logger log = LogManager.getLogger(PreVettedProductController.class);
    private final PreVettedProductService preVettedProductService;

    @GetMapping("/pre-vetted")
    public ResponseEntity<Page<PreVettedProduct>> findPreVettedProduct(Pageable pageable) {
        log.info("PRE VETTED END POINT WAS CALLED ");
        return ResponseEntity.ok(this.preVettedProductService.findPreVettedProducts(pageable));

    }
}



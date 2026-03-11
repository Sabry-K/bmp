package com.vcs.bmp.microservices.catalog.service;

import com.broadleafcommerce.catalog.domain.CategoryProduct;
import com.broadleafcommerce.catalog.domain.product.Product;
import com.broadleafcommerce.catalog.domain.product.Variant;
import com.broadleafcommerce.catalog.provider.jpa.repository.product.JpaProductRepository;
import com.broadleafcommerce.catalog.repository.product.ProductRepository;
import com.broadleafcommerce.catalog.service.CategoryProductService;
import com.broadleafcommerce.catalog.service.product.DefaultProductService;
import com.broadleafcommerce.catalog.service.product.VariantService;
import com.broadleafcommerce.common.extension.TypeFactory;
import com.broadleafcommerce.common.extension.cache.CacheStateManager;
import com.broadleafcommerce.data.tracking.core.Trackable;
import com.broadleafcommerce.data.tracking.core.filtering.fetch.FilterParser;
import com.broadleafcommerce.data.tracking.core.service.RsqlCrudEntityHelper;
import com.vcs.bmp.microservices.catalog.domain.PreVettedProduct;
import com.vcs.bmp.microservices.catalog.repository.PreVettedDynamicRepository;
import cz.jirutka.rsql.parser.ast.Node;
import jakarta.validation.constraints.Null;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class PreVettedProductService extends DefaultProductService<Product> {

    private final JpaProductRepository<PreVettedProduct> preVettedDynamicRepository;

    public PreVettedProductService(ProductRepository<Trackable> repository, RsqlCrudEntityHelper helper,
                                   VariantService<Variant> variantService,
                                   CategoryProductService<CategoryProduct> categoryProductService,
                                   @Nullable CacheStateManager cacheStateManager,
                                   @Nullable  FilterParser<Node> parser, TypeFactory typeFactory, JpaProductRepository<PreVettedProduct> preVettedDynamicRepository) {
        super(repository, helper, variantService, categoryProductService, cacheStateManager, parser, typeFactory);
        this.preVettedDynamicRepository = preVettedDynamicRepository;
    }

    public Page<PreVettedProduct> findPreVettedProducts(Pageable pageable) {
        return ((PreVettedDynamicRepository)this.preVettedDynamicRepository).findAllByPreVetted(Boolean.TRUE, pageable);
    }

}

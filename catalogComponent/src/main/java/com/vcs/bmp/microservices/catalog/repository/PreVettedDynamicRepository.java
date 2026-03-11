package com.vcs.bmp.microservices.catalog.repository;


import com.vcs.bmp.microservices.catalog.domain.PreVettedProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

public interface PreVettedDynamicRepository {

    Page<PreVettedProduct> findAllByPreVetted(@NonNull Boolean preVetted , @NonNull Pageable pageable);

}

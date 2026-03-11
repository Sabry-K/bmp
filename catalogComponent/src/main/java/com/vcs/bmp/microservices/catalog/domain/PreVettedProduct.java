package com.vcs.bmp.microservices.catalog.domain;


import com.broadleafcommerce.catalog.provider.jpa.domain.product.JpaProduct;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Entity
@Table(name = "PRE_VETTED_PRODUCT")
@Inheritance(strategy = InheritanceType.JOINED)

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PreVettedProduct extends JpaProduct {

    private Boolean preVetted = false;
}

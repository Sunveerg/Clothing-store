package com.example.productservice.productsubdomain.dataaccesslayer;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.UUID;

@Embeddable
@Getter
public class ProductIdentifier {

    @Column(name = "product_id")
    private String productId;

    public ProductIdentifier(){this.productId= UUID.randomUUID().toString();}

    public ProductIdentifier(String productId) {
        this.productId = productId;
    }

}

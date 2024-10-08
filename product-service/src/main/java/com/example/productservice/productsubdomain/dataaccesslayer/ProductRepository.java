package com.example.productservice.productsubdomain.dataaccesslayer;


import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findByProductIdentifier_productId(String productId);

}

package com.example.productservice.productsubdomain.dataaccesslayer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryIntegrationTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    public void ProductRepository_GetAll_ReturnsMoreThenOneProduct(){
        // Given
        Product product1 = new Product();
        product1.setProductName("Test Product 1");
        product1.setCategory("TestCategory");
        product1.setBrand(Brand.NIKE);
        product1.setPrice(10.0);
        product1.setStock(100);
        product1.setProductDescription("Test Description 1");

        Product product2 = new Product();
        product2.setProductName("Test Product 2");
        product2.setCategory("TestCategory");
        product2.setBrand(Brand.ADIDAS);
        product2.setPrice(20.0);
        product2.setStock(200);
        product2.setProductDescription("Test Description 2");

        productRepository.save(product1);
        productRepository.save(product2);

        // When
        List<Product> productList = productRepository.findAll();

        // Then
        assertNotNull(productList);
        assertEquals(2, productList.size()); // Check that there are two products in the list
        assertTrue(productList.contains(product1)); // Check if product1 is present in the list
        assertTrue(productList.contains(product2));
    }


    @Test
    public void whenLeDoesNotExist_ReturnNull(){
        Product savedProduct = productRepository.findByProductIdentifier_productId("100");

        assertNull(savedProduct);
    }
}
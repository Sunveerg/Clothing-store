package com.example.productservice.productsubdomain.presentationlayer;

import com.example.productservice.productsubdomain.businesslayer.ProductService;
import com.example.productservice.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = ProductController.class)
class ProductControllerUnitTest {

    @Autowired
    ProductController productController;

    @MockBean
    ProductService productService;

    private final String FOUND_PRODUCT_ID = "1";
    private final String NOT_FOUND_PRODUCT_ID = "100";


    @Test
    public void whenNoproductExists_thenReturnEmptyList(){
        //arrange
        when(productService.getAllProduct()).thenReturn(Collections.EMPTY_LIST);

        //act
        ResponseEntity<List<ProductResponseModel>> responseEntity= productController.getAllProducts();

        //assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isEmpty());
        verify(productService, times(1)).getAllProduct();
    }

    @Test
    public void whenproductExists_thenReturnproduct(){
        //arrange

        ProductRequestModel productRequestModel= ProductRequestModel.builder().build();
        ProductResponseModel productResponseModel= ProductResponseModel.builder().build();

        when(productService.addProduct(productRequestModel)).thenReturn(productResponseModel);

        //act
        ResponseEntity<ProductResponseModel> responseEntity= productController.addProduct(productRequestModel);

        //assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(productResponseModel, responseEntity.getBody());
        verify(productService, times(1)).addProduct(productRequestModel);
    }

    @Test
    public void whenproductExists_thenDeleteproduct() throws NotFoundException {
        // Arrange
        doNothing().when(productService).deleteProduct(FOUND_PRODUCT_ID);

        // Act
        ResponseEntity<Void> responseEntity = productController.deleteproduct(FOUND_PRODUCT_ID);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(productService, times(1)).deleteProduct(FOUND_PRODUCT_ID);
    }

    @Test
    public void WhenproductDoesNotExistOnDelete_thenReturnNotFoundException() throws NotFoundException {
        // Arrange
        String nonExistentProductId = "non-existent-product-id";
        doThrow(NotFoundException.class).when(productService).deleteProduct(nonExistentProductId);

        // Act and Assert
        try {
            productController.deleteproduct(nonExistentProductId);
            fail("Expected NotFoundException");
        } catch (NotFoundException e) {
            // Verify that NotFoundException was thrown
            verify(productService, times(1)).deleteProduct(nonExistentProductId);
        }
    }

    @Test
    public void whenproductNotFoundOnGet_thenReturnNotFoundException() {
        // Arrange
        when(productService.getProductById(NOT_FOUND_PRODUCT_ID)).thenThrow(NotFoundException.class);

        // Act and Assert
        try {
            productController.getProductById(NOT_FOUND_PRODUCT_ID);
            fail("Expected NotFoundException");
        } catch (NotFoundException e) {
            // Verify that NotFoundException was thrown
            verify(productService, times(1)).getProductById(NOT_FOUND_PRODUCT_ID);
        }
    }

    @Test
    public void whenproductNotExistOnUpdate_thenReturnNotFoundException() throws NotFoundException {
        // Arrange
        ProductRequestModel updatedproduct = new ProductRequestModel();
        when(productService.updateProduct(updatedproduct, NOT_FOUND_PRODUCT_ID)).thenThrow(NotFoundException.class);

        // Act and Assert
        try {
            productController.updateProduct(updatedproduct, NOT_FOUND_PRODUCT_ID);
            fail("Expected NotFoundException");
        } catch (NotFoundException e) {
            // Verify that NotFoundException was thrown
            verify(productService, times(1)).updateProduct(updatedproduct, NOT_FOUND_PRODUCT_ID);
        }
    }


    @Test
    public void whenproductExist_thenReturnUpdateproduct() throws NotFoundException {
        // Arrange
        String existingproductId = "existing-product-id";
        ProductRequestModel updatedproduct = new ProductRequestModel();
        ProductResponseModel updatedResponse = ProductResponseModel.builder().productId(FOUND_PRODUCT_ID).build();

        when(productService.updateProduct(updatedproduct, FOUND_PRODUCT_ID)).thenReturn(updatedResponse);

        // Act
        ResponseEntity<ProductResponseModel> responseEntity = productController.updateProduct(updatedproduct, FOUND_PRODUCT_ID);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(FOUND_PRODUCT_ID, responseEntity.getBody().getProductId());
        verify(productService, times(1)).updateProduct(updatedproduct, FOUND_PRODUCT_ID);
    }

}
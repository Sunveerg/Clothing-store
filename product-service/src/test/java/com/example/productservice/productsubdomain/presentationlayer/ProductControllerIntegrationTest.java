package com.example.productservice.productsubdomain.presentationlayer;

import com.example.productservice.productsubdomain.dataaccesslayer.ProductRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.example.productservice.productsubdomain.dataaccesslayer.Brand.NIKE;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("h2")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ProductControllerIntegrationTest {

    private final String BASE_URI_PRODUCTS = "/api/v1/products";
    private final String FOUND_PRODUCT_ID = "1";
    private final String NOT_FOUND_PRODUCT_ID = "100";

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void whenGetProducts_thenReturnAllProducts() {
        //arrange
        Long sizeDB = productRepository.count();
        System.out.println("size of H2 db : " + sizeDB);
        //act
        webTestClient.get()
                .uri(BASE_URI_PRODUCTS)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(ProductResponseModel.class)
                .value((list) -> {
                    assertNotNull(list);
                    assertTrue(list.size() == sizeDB);
                });

    }

    @Test
    public void whenGetProductDoesNotExist_thenReturnNotFound() {
        //act & assert
        webTestClient.get()
                .uri(BASE_URI_PRODUCTS + "/" + NOT_FOUND_PRODUCT_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.httpStatus").isEqualTo("NOT_FOUND")
                .jsonPath("$.message").isEqualTo("Unknown productId " + NOT_FOUND_PRODUCT_ID);
    }

    @Test
    public void whenValidProduct_thenCreateProduct() {
        //arrange
        long sizeDB = productRepository.count();

        ProductRequestModel ProductRequestModel = new ProductRequestModel(
                "Test Product",
                "Test category",
                NIKE,
                24.99,
                "Test Description",
                10);
        
        webTestClient.post()
                .uri(BASE_URI_PRODUCTS)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(ProductRequestModel)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ProductResponseModel.class)
                .value((ProductResponseModel) -> {
                    assertNotNull(ProductResponseModel);
                    assertEquals(ProductRequestModel.getProductName(), ProductResponseModel.getProductName());
                    assertEquals(ProductRequestModel.getPrice(), ProductResponseModel.getPrice());
                    assertEquals(ProductRequestModel.getStock(), ProductResponseModel.getStock());
                });
        long sizeDbAfter = productRepository.count();
        assertEquals(sizeDB + 1, sizeDbAfter);

    }


    @Test
    public void whenProductDoesNotExistOnUpdate_thenReturnNotFound() {
        // Arrange
        ProductRequestModel ProductRequestModel = new ProductRequestModel(
                "Test Product",
                "Test category",
                NIKE,
                24.99,
                "Test Description",
                10);
        
        // Perform a PUT or PATCH request to update a Product that does not exist
        webTestClient.put()
                .uri(BASE_URI_PRODUCTS + "/" + NOT_FOUND_PRODUCT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(ProductRequestModel) // Set the request body with the updated Product data
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.httpStatus").isEqualTo("NOT_FOUND")
                .jsonPath("$.message").isEqualTo("Unknown productId " + NOT_FOUND_PRODUCT_ID);
    }


    @Test
    public void whenProductExist_thenDeleteProduct() {
        // Arrange: Ensure that the Product exists in the database and retrieve its ID
        String existingProductId = "1"; // Replace with the actual ID of the existing Product

        // Act: Delete the Product
        webTestClient.delete()
                .uri(BASE_URI_PRODUCTS + "/" + FOUND_PRODUCT_ID)
                .exchange()
                .expectStatus().isNoContent();

        // Assert: Verify the Product is deleted
        webTestClient.get()
                .uri(BASE_URI_PRODUCTS + "/" + FOUND_PRODUCT_ID)
                .exchange()
                .expectStatus().isNotFound(); // Assuming a 404 Not Found is returned when the Product is not found
    }


    @Test
    public void whenValidProduct_thenUpdateProduct() {
        // Arrange: Assuming "FOUND_PRODUCT_ID" contains the ID of an existing Product
        String ProductIdToUpdate = FOUND_PRODUCT_ID;

        // Create the Product update request model
        ProductRequestModel ProductRequestModel = new ProductRequestModel(
                "Test Product",
                "Test category",
                NIKE,
                24.99,
                "Test Description",
                10);
        
        // Perform the PUT request to update the Product
        webTestClient.put()
                .uri(BASE_URI_PRODUCTS + "/" + ProductIdToUpdate) // Include the Product ID in the URI
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(ProductRequestModel)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ProductResponseModel.class)
                .value((ProductResponseModel) -> {
                    assertNotNull(ProductResponseModel);
                    assertEquals(ProductRequestModel.getProductName(), ProductResponseModel.getProductName());
                    assertEquals(ProductRequestModel.getPrice(), ProductResponseModel.getPrice());
                    assertEquals(ProductRequestModel.getStock(), ProductResponseModel.getStock());
                });
    }


    @Test
    public void whenProductExists_thenReturnProduct() {
        // Arrange: Assuming there is a Product with the specified ID in the database
        String existingProductId = "1"; // Replace with the actual ID of the existing Product

        // Act: Perform a GET request to retrieve the Product by ID
        webTestClient.get()
                .uri(BASE_URI_PRODUCTS + "/" + FOUND_PRODUCT_ID) // Adjust the URI to match your endpoint for retrieving Products by ID
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ProductResponseModel.class)
                .value((Product) -> {
                    // Assert: Verify that the returned Product is not null and has the correct properties
                    assertNotNull(Product);
                    assertEquals("T-Shirt", Product.getProductName());
                });
    }


    @Test
    public void whenProductsDontExistOnDelete_thenReturnNotFound() {
        // Arrange: Assuming there are no Products in the database
        String nonExistingProductId = "nonExistingId"; // Replace with an ID that doesn't exist in the database

        // Act: Perform a DELETE request to delete a Product that doesn't exist
        webTestClient.delete()
                .uri(BASE_URI_PRODUCTS + "/" + nonExistingProductId) // Adjust the URI to match your endpoint for deleting Products by ID
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.httpStatus").isEqualTo("NOT_FOUND")
                .jsonPath("$.message").isEqualTo("Unknown productId " + nonExistingProductId);
    }

    @Test
    public void whenNoProductExist_thenReturnEmptyList() {
        productRepository.deleteAll();
        // Act: Perform a GET request to retrieve all Products
        webTestClient.get()
                .uri(BASE_URI_PRODUCTS)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(ProductResponseModel.class)
                .value(list -> assertTrue(list.isEmpty())); // Assert that the returned list is empty
    }

}
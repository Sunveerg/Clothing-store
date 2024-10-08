package com.example.customerservice.presentationlayer;


import com.example.customerservice.customersubdomain.datalayer.CustomerRepository;
import com.example.customerservice.customersubdomain.presentationlayer.CustomerRequestModel;
import com.example.customerservice.customersubdomain.presentationlayer.CustomerResponseModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("h2")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CustomerControllerIntegrationTest {

    private final String BASE_URI_CUSTOMERS = "/api/v1/customers";
    private final String FOUND_CUSTOMER_ID = "c3540a89-cb47-4c96-888e-ff96708db4d8";
    private final String NOT_FOUND_CUSTOMER_ID = "100";

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void whenGetCustomers_thenReturnAllCustomers() {
        //arrange
        Long sizeDB = customerRepository.count();
        System.out.println("size of H2 db : " + sizeDB);
        //act
        webTestClient.get()
                .uri(BASE_URI_CUSTOMERS)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(CustomerResponseModel.class)
                .value((list) -> {
                    assertNotNull(list);
                    assertTrue(list.size() == sizeDB);
                });

    }

    @Test
    public void whenGetCustomerDoesNotExist_thenReturnNotFound() {
        //act & assert
        webTestClient.get()
                .uri(BASE_URI_CUSTOMERS + "/" + NOT_FOUND_CUSTOMER_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.httpStatus").isEqualTo("NOT_FOUND")
                .jsonPath("$.message").isEqualTo("Unknown customerId: " + NOT_FOUND_CUSTOMER_ID);
    }

    @Test
    public void whenValidCustomer_thenCreateCustomer() {
        //arrange
        long sizeDB = customerRepository.count();

        CustomerRequestModel CustomerRequestModel = new CustomerRequestModel(
                "john",
                "doe",
                "jd@hotmail.com",
                "123",
                "Chambly",
                "Quebec",
                "Canada",
                "J45 678"
        );
        
        webTestClient.post()
                .uri(BASE_URI_CUSTOMERS)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(CustomerRequestModel)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(CustomerResponseModel.class)
                .value((CustomerResponseModel) -> {
                    assertNotNull(CustomerResponseModel);
                    assertEquals(CustomerRequestModel.getCity(), CustomerResponseModel.getCity());
                    assertEquals(CustomerRequestModel.getEmailAddress(), CustomerResponseModel.getEmailAddress());
                    assertEquals(CustomerRequestModel.getCountry(), CustomerResponseModel.getCountry());
                });
        long sizeDbAfter = customerRepository.count();
        assertEquals(sizeDB + 1, sizeDbAfter);

    }


    @Test
    public void whenCustomerDoesNotExistOnUpdate_thenReturnNotFound() {
        // Arrange
        CustomerRequestModel CustomerRequestModel = new CustomerRequestModel(
                "john",
                "doe",
                "jd@hotmail.com",
                "123",
                "Chambly",
                "Quebec",
                "Canada",
                "J45 678"
        );
        
        // Perform a PUT or PATCH request to update a Customer that does not exist
        webTestClient.put()
                .uri(BASE_URI_CUSTOMERS + "/" + NOT_FOUND_CUSTOMER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(CustomerRequestModel) // Set the request body with the updated Customer data
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.httpStatus").isEqualTo("NOT_FOUND")
                .jsonPath("$.message").isEqualTo("Unknown customerId: " + NOT_FOUND_CUSTOMER_ID);
    }


    @Test
    public void whenCustomerExist_thenDeleteCustomer() {
        // Arrange: Ensure that the Customer exists in the database and retrieve its ID
        String existingCustomerId = "1"; // Replace with the actual ID of the existing Customer

        // Act: Delete the Customer
        webTestClient.delete()
                .uri(BASE_URI_CUSTOMERS + "/" + FOUND_CUSTOMER_ID)
                .exchange()
                .expectStatus().isNotFound();

        // Assert: Verify the Customer is deleted
        webTestClient.get()
                .uri(BASE_URI_CUSTOMERS + "/" + FOUND_CUSTOMER_ID)
                .exchange()
                .expectStatus().isNotFound(); // Assuming a 404 Not Found is returned when the Customer is not found
    }


    @Test
    public void whenValidCustomer_thenUpdateCustomer() {
        // Arrange: Assuming "FOUND_CUSTOMER_ID" contains the ID of an existing Customer
        String CustomerIdToUpdate = FOUND_CUSTOMER_ID;

        // Create the Customer update request model
        CustomerRequestModel CustomerRequestModel = new CustomerRequestModel(
                "john",
                "doe",
                "jd@hotmail.com",
                "123",
                "Chambly",
                "Quebec",
                "Canada",
                "J45 678"
        );
        
        // Perform the PUT request to update the Customer
        webTestClient.put()
                .uri(BASE_URI_CUSTOMERS + "/" + CustomerIdToUpdate) // Include the Customer ID in the URI
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(CustomerRequestModel)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(CustomerResponseModel.class)
                .value((CustomerResponseModel) -> {
                    assertNotNull(CustomerResponseModel);
                    assertEquals(CustomerRequestModel.getCity(), CustomerResponseModel.getCity());
                    assertEquals(CustomerRequestModel.getEmailAddress(), CustomerResponseModel.getEmailAddress());
                    assertEquals(CustomerRequestModel.getCountry(), CustomerResponseModel.getCountry());
                });
    }


    @Test
    public void whenCustomerExists_thenReturnCustomer() {
        // Arrange: Assuming there is a Customer with the specified ID in the database
        String existingCustomerId = "1"; // Replace with the actual ID of the existing Customer

        // Act: Perform a GET request to retrieve the Customer by ID
        webTestClient.get()
                .uri(BASE_URI_CUSTOMERS + "/" + FOUND_CUSTOMER_ID) // Adjust the URI to match your endpoint for retrieving Customers by ID
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(CustomerResponseModel.class)
                .value((Customer) -> {
                    // Assert: Verify that the returned Customer is not null and has the correct properties
                    assertNotNull(Customer);
                    assertEquals("john", Customer.getFirstName());
                });
    }


    @Test
    public void whenCustomersDontExistOnDelete_thenReturnNotFound() {
        // Arrange: Assuming there are no Customers in the database
        String nonExistingCustomerId = "nonExistingId"; // Replace with an ID that doesn't exist in the database

        // Act: Perform a DELETE request to delete a Customer that doesn't exist
        webTestClient.delete()
                .uri(BASE_URI_CUSTOMERS + "/" + nonExistingCustomerId) // Adjust the URI to match your endpoint for deleting Customers by ID
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.httpStatus").isEqualTo("NOT_FOUND")
                .jsonPath("$.message").isEqualTo("Unknown customerId: " + nonExistingCustomerId);
    }

    @Test
    public void whenNoCustomerExist_thenReturnEmptyList() {
        customerRepository.deleteAll();
        // Act: Perform a GET request to retrieve all Customers
        webTestClient.get()
                .uri(BASE_URI_CUSTOMERS)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(CustomerResponseModel.class)
                .value(list -> assertTrue(list.isEmpty())); // Assert that the returned list is empty
    }

}
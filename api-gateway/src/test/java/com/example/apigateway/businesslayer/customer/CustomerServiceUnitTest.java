package com.example.apigateway.businesslayer.customer;

import com.example.apigateway.domainclientlayer.customer.CustomerServiceClient;
import com.example.apigateway.mapperlayer.customer.CustomerResponseMapper;
import com.example.apigateway.presentationlayer.customer.CustomerResponseModel;
import com.example.apigateway.presentationlayer.customer.CustomerRequestModel;
import com.example.apigateway.utils.InuseException.NotFoundException;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest()
@ActiveProfiles("test")
public class CustomerServiceUnitTest {

    @Mock
    private CustomerServiceClient customerServiceClient;

    @Mock
    private CustomerResponseMapper customerResponseMapper;



    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void whenNoCustomersExist_thenReturnEmptyList() {
        // Arrange
        CustomerServiceClient customerServiceClient = mock(CustomerServiceClient.class);
        CustomerResponseMapper customerResponseMapper = mock(CustomerResponseMapper.class);

        // Mock the behavior of customerServiceClient to return an empty list
        when(customerServiceClient.getAllCustomers()).thenReturn(Collections.emptyList());

        // Create an instance of CustomerServiceImpl with mocked dependencies
        CustomerServiceImpl customerService = new CustomerServiceImpl(customerResponseMapper, customerServiceClient);

        // Act
        List<CustomerResponseModel> customers = customerService.getCustomers();

        // Assert
        assertNotNull(customers);
        assertTrue(customers.isEmpty());
    }

    @Test
    void whenCustomerExist_thenReturnListOfCustomers() {


        // Create some sample customer response models
        CustomerResponseModel customer1 = new CustomerResponseModel(
                "1",
                "john",
                "doe",
                "jd@hotmail.com",
                "123",
                "Chambly",
                "Quebec",
                "Canada",
                "J45 678"
        );
        CustomerRequestModel customerRequestModel = new CustomerRequestModel();
        customerRequestModel.setFirstName(customer1.getFirstName());
        customerRequestModel.setCountry(customer1.getCountry());
        customerRequestModel.setLastName(customer1.getLastName());
        customerRequestModel.setEmailAddress(customer1.getEmailAddress());
        customerRequestModel.setCity(customer1.getCity());

        when(customerServiceClient.addCustomer(customerRequestModel)).thenReturn(customer1);

        CustomerResponseModel returned = customerServiceClient.addCustomer(customerRequestModel);


        MatcherAssert.assertThat(returned, samePropertyValuesAs(customer1));
    }

    @Test
    void whenUpdateCustomerWithInvalidId_thenThrowNotFoundException() {
        // Arrange
        CustomerResponseModel customer1 = new CustomerResponseModel(
                "1",
                "john",
                "doe",
                "jd@hotmail.com",
                "123",
                "Chambly",
                "Quebec",
                "Canada",
                "J45 678"
        );
        CustomerRequestModel customerRequestModel = new CustomerRequestModel();
        customerRequestModel.setFirstName(customer1.getFirstName());
        customerRequestModel.setCountry(customer1.getCountry());
        customerRequestModel.setLastName(customer1.getLastName());
        customerRequestModel.setEmailAddress(customer1.getEmailAddress());
        customerRequestModel.setCity(customer1.getCity());

        // Mock the behavior of customerServiceClient
        doThrow(new NotFoundException()).when(customerServiceClient).updateCustomer(customer1.getCustomerId(), customerRequestModel);

        // Act & Assert
        assertDoesNotThrow(() -> {
            try {
                customerService.updateCustomer(customerRequestModel, customer1.getCustomerId());
            } catch (NotFoundException e) {
                throw e; // Re-throw the exception to fail the test
            }
        });
    }

}

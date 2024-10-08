package com.example.customerservice.presentationlayer;

import com.example.customerservice.customersubdomain.businesslayer.CustomerService;
import com.example.customerservice.customersubdomain.presentationlayer.CustomerController;
import com.example.customerservice.customersubdomain.presentationlayer.CustomerRequestModel;
import com.example.customerservice.customersubdomain.presentationlayer.CustomerResponseModel;
import com.example.customerservice.utils.exceptions.NotFoundException;
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

@SpringBootTest(classes = CustomerController.class)
class CustomerControllerUnitTest {

    @Autowired
    CustomerController customerController;

    @MockBean
    CustomerService customerService;

    private final String FOUND_CUSTOMER_ID = "c3540a89-cb47-4c96-888e-ff96708db4d8";
    private final String NOT_FOUND_CUSTOMER_ID = "100";


    @Test
    public void whenNocustomerExists_thenReturnEmptyList(){
        //arrange
        when(customerService.getCustomers()).thenReturn(Collections.EMPTY_LIST);

        //act
        ResponseEntity<List<CustomerResponseModel>> responseEntity= customerController.getCustomers();

        //assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isEmpty());
        verify(customerService, times(1)).getCustomers();
    }

    @Test
    public void whencustomerExists_thenReturncustomer(){
        //arrange

        CustomerRequestModel customerRequestModel= CustomerRequestModel.builder().build();
        CustomerResponseModel customerResponseModel= CustomerResponseModel.builder().build();

        when(customerService.addCustomer(customerRequestModel)).thenReturn(customerResponseModel);

        //act
        ResponseEntity<CustomerResponseModel> responseEntity= customerController.addCustomer(customerRequestModel);

        //assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(customerResponseModel, responseEntity.getBody());
        verify(customerService, times(1)).addCustomer(customerRequestModel);
    }

    @Test
    public void whencustomerExists_thenDeletecustomer() throws NotFoundException {
        // Arrange
        doNothing().when(customerService).removeCustomer(FOUND_CUSTOMER_ID);

        // Act
        ResponseEntity<Void> responseEntity = customerController.deleteCustomer(FOUND_CUSTOMER_ID);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(customerService, times(1)).removeCustomer(FOUND_CUSTOMER_ID);
    }

    @Test
    public void WhencustomerDoesNotExistOnDelete_thenReturnNotFoundException() throws NotFoundException {
        // Arrange
        String nonExistentCustomerId = "non-existent-customer-id";
        doThrow(NotFoundException.class).when(customerService).removeCustomer(nonExistentCustomerId);

        // Act and Assert
        try {
            customerController.deleteCustomer(nonExistentCustomerId);
            fail("Expected NotFoundException");
        } catch (NotFoundException e) {
            // Verify that NotFoundException was thrown
            verify(customerService, times(1)).removeCustomer(nonExistentCustomerId);
        }
    }

    @Test
    public void whencustomerNotFoundOnGet_thenReturnNotFoundException() {
        // Arrange
        when(customerService.getCustomerByCustomerId(NOT_FOUND_CUSTOMER_ID)).thenThrow(NotFoundException.class);

        // Act and Assert
        try {
            customerController.getCustomerByCustomerId(NOT_FOUND_CUSTOMER_ID);
            fail("Expected NotFoundException");
        } catch (NotFoundException e) {
            // Verify that NotFoundException was thrown
            verify(customerService, times(1)).getCustomerByCustomerId(NOT_FOUND_CUSTOMER_ID);
        }
    }

    @Test
    public void whencustomerNotExistOnUpdate_thenReturnNotFoundException() throws NotFoundException {
        // Arrange
        CustomerRequestModel updatedcustomer = new CustomerRequestModel();
        when(customerService.updateCustomer(updatedcustomer, NOT_FOUND_CUSTOMER_ID)).thenThrow(NotFoundException.class);

        // Act and Assert
        try {
            customerController.updateCustomer(updatedcustomer, NOT_FOUND_CUSTOMER_ID);
            fail("Expected NotFoundException");
        } catch (NotFoundException e) {
            // Verify that NotFoundException was thrown
            verify(customerService, times(1)).updateCustomer(updatedcustomer, NOT_FOUND_CUSTOMER_ID);
        }
    }


    @Test
    public void whencustomerExist_thenReturnUpdatecustomer() throws NotFoundException {
        // Arrange
        String existingcustomerId = "existing-customer-id";
        CustomerRequestModel updatedcustomer = new CustomerRequestModel();
        CustomerResponseModel updatedResponse = CustomerResponseModel.builder().customerId(FOUND_CUSTOMER_ID).build();

        when(customerService.updateCustomer(updatedcustomer, FOUND_CUSTOMER_ID)).thenReturn(updatedResponse);

        // Act
        ResponseEntity<CustomerResponseModel> responseEntity = customerController.updateCustomer(updatedcustomer, FOUND_CUSTOMER_ID);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(FOUND_CUSTOMER_ID, responseEntity.getBody().getCustomerId());
        verify(customerService, times(1)).updateCustomer(updatedcustomer, FOUND_CUSTOMER_ID);
    }

}
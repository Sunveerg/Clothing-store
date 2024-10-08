package com.example.customerservice.dataaccesslayer;

import com.example.customerservice.customersubdomain.datalayer.Address;
import com.example.customerservice.customersubdomain.datalayer.Customer;
import com.example.customerservice.customersubdomain.datalayer.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryIntegrationTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    public void ProductRepository_GetAll_ReturnsMoreThenOneProduct(){
        // Given

        Address address = new Address();
        
        Customer customer1 = new Customer(
                "john",
                "doe",
                "jd@hotmail.com",
                address
        );
        
        customerRepository.save(customer1);

        Customer customer = customerRepository.findByCustomerIdentifier_CustomerId(customer1.getCustomerIdentifier().getCustomerId());

        assertNotNull(customer);
        assertEquals(customer.getCustomerIdentifier().getCustomerId(), customer1.getCustomerIdentifier().getCustomerId());
        assertEquals(customer.getFirstName(), customer1.getFirstName());
    }


    @Test
    public void whenLeDoesNotExist_ReturnNull(){
        Customer savedCustomer =customerRepository.findByCustomerIdentifier_CustomerId("100");

        assertNull(savedCustomer);
    }
}
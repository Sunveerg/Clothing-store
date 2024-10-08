package com.example.orderservice.ordersubdomain.domainclientlayer.Customer;


import com.example.orderservice.utils.HttpErrorInfo;
import com.example.orderservice.utils.exceptions.InvalidInputException;
import com.example.orderservice.utils.exceptions.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Component
@Slf4j
public class CustomerServiceClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    private final String CUSTOMER_SERVICE_BASE_URL;

    private CustomerServiceClient(RestTemplate restTemplate, ObjectMapper objectMapper,
                                    @Value("${app.customer-service.host}") String customerServiceHost,
                                    @Value("${app.customer-service.port}") String customerServicePort){
        this.restTemplate= restTemplate;
        this.mapper= objectMapper;

        CUSTOMER_SERVICE_BASE_URL = "http://" + customerServiceHost + ":" +  customerServicePort + "/api/v1/customers";

    }

    public CustomerModel getCustomerByCustomerId(String customerId){
        try {
            String url = CUSTOMER_SERVICE_BASE_URL + "/" + customerId;
            CustomerModel customerModel = restTemplate.getForObject(url, CustomerModel.class);
            return customerModel;
        }catch (HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }

    }


    private RuntimeException handleHttpClientException(HttpClientErrorException ex) {
        if (ex.getStatusCode() == NOT_FOUND) {
            return new NotFoundException(getErrorMessage(ex));
        }
        if (ex.getStatusCode() == UNPROCESSABLE_ENTITY) {
            return new InvalidInputException(getErrorMessage(ex));
        }
        log.warn("Got an unexpected HTTP error: {}, will rethrow it", ex.getStatusCode());
        log.warn("Error body: {}", ex.getResponseBodyAsString());
        return ex;
    }

    private String getErrorMessage(HttpClientErrorException ex) {
        try {
            return mapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
        }
        catch (IOException ioex) {
            return ioex.getMessage();
        }
    }

}

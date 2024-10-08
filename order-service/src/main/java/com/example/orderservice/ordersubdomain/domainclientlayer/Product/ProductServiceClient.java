package com.example.orderservice.ordersubdomain.domainclientlayer.Product;

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
public class ProductServiceClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    private final String PRODUCT_SERVICE_BASE_URL;

    private ProductServiceClient(RestTemplate restTemplate, ObjectMapper objectMapper,
                                @Value("${app.product-service.host}") String productServiceHost,
                                @Value("${app.product-service.port}") String productServicePort){
        this.restTemplate= restTemplate;
        this.mapper= objectMapper;

        PRODUCT_SERVICE_BASE_URL= "http://" + productServiceHost + ":" +  productServicePort + "/api/v1/products";

    }

    public ProductModel getProductByProductId(String productId){
        try {
            String url= PRODUCT_SERVICE_BASE_URL + "/" + productId;
            ProductModel model=  restTemplate.getForObject(url,ProductModel.class);
            return model;
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

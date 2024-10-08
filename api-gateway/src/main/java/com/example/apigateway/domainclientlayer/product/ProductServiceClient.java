package com.example.apigateway.domainclientlayer.product;

import com.example.apigateway.presentationlayer.product.ProductRequestModel;
import com.example.apigateway.presentationlayer.product.ProductResponseModel;
import com.example.apigateway.utils.HttpErrorInfo;
import com.example.apigateway.utils.InuseException.InvalidInputException;
import com.example.apigateway.utils.InuseException.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Component
@Slf4j
public class ProductServiceClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    private final String EMPLOYEE_SERVICE_BASE_URL;

    private ProductServiceClient(RestTemplate restTemplate, ObjectMapper objectMapper,
                                  @Value("${app.product-service.host}") String productServiceHost,
                                  @Value("${app.product-service.port}") String productServicePort){
        this.restTemplate = restTemplate;
        this.mapper = objectMapper;

        EMPLOYEE_SERVICE_BASE_URL= "http://" + productServiceHost + ":" +  productServicePort + "/api/v1/products";

    }

    public ProductResponseModel updateProduct(String productId, ProductRequestModel productRequestModel) {
        try {
            String url = EMPLOYEE_SERVICE_BASE_URL + "/" + productId;

            // Set the headers for the request
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create the request entity with the league data in the body and headers
            HttpEntity<ProductRequestModel> requestEntity = new HttpEntity<>(productRequestModel, headers);

            // Send the PUT request to update the league
            restTemplate.put(url, requestEntity);

            // Assuming the leagues service returns the updated league data, you can fetch it
            ProductResponseModel updatedProduct = restTemplate.getForObject(url, ProductResponseModel.class);

            return updatedProduct;
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }




    public ProductResponseModel addProduct(ProductRequestModel productRequestModel) {
        try {
            String url = EMPLOYEE_SERVICE_BASE_URL;

            // Set the headers for the request
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create the request entity with the league data in the body and headers
            HttpEntity<ProductRequestModel> requestEntity = new HttpEntity<>(productRequestModel, headers);

            // Send the POST request to add the league
            ProductResponseModel leagueModel = restTemplate.postForObject(url, requestEntity, ProductResponseModel.class);

            return leagueModel;
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public void removeProduct(String productId) {
        try {
            String url = EMPLOYEE_SERVICE_BASE_URL + "/" + productId;
            restTemplate.delete(url);
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public List<ProductResponseModel> getAllProducts(){
        try {
            String url= EMPLOYEE_SERVICE_BASE_URL;
            ProductResponseModel[] productResponseModels=  restTemplate.getForObject(url,ProductResponseModel[].class);
            return Arrays.asList(productResponseModels);

        }catch (HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }
    }

    public ProductResponseModel getProductByProductId(String productId){
        try {
            String url= EMPLOYEE_SERVICE_BASE_URL + "/" + productId;
            ProductResponseModel productModel=  restTemplate.getForObject(url,ProductResponseModel.class);
            return productModel;
        }catch (HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }

    }

    private RuntimeException handleHttpClientException(HttpClientErrorException ex) {
//include all possible responses from the client
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

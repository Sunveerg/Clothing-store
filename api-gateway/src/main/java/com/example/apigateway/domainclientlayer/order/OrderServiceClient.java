package com.example.apigateway.domainclientlayer.order;

import com.example.apigateway.presentationlayer.order.OrderRequestModel;
import com.example.apigateway.presentationlayer.order.OrderResponseModel;
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
public class OrderServiceClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    private final String ORDER_SERVICE_BASE_URL;

    private OrderServiceClient(RestTemplate restTemplate, ObjectMapper objectMapper,
                                   @Value("${app.order-service.host}") String orderServiceHost,
                                   @Value("${app.order-service.port}") String orderServicePort){
        this.restTemplate= restTemplate;
        this.mapper= objectMapper;

        ORDER_SERVICE_BASE_URL= "http://" + orderServiceHost + ":" +  orderServicePort + "/api/v1/orders/{customerId}";

    }

    public OrderResponseModel updateOrder(String orderId, OrderRequestModel orderRequestModel, String customerId) {
        try {
            String url = ORDER_SERVICE_BASE_URL.replace("{customerId}", customerId) +  "/" +  orderId;

            // Set the headers for the request
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create the request entity with the league data in the body and headers
            HttpEntity<OrderRequestModel> requestEntity = new HttpEntity<>(orderRequestModel, headers);

            // Send the PUT request to update the league
            restTemplate.put(url, requestEntity);

            // Assuming the leagues service returns the updated league data, you can fetch it

            return restTemplate.getForObject(url, OrderResponseModel.class);
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }




    public OrderResponseModel addOrder(OrderRequestModel orderRequestModel, String customerId) {
        try {
            String url = ORDER_SERVICE_BASE_URL.replace("{customerId}", customerId);

            // Set the headers for the request
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create the request entity with the league data in the body and headers
            HttpEntity<OrderRequestModel> requestEntity = new HttpEntity<>(orderRequestModel, headers);

            // Send the POST request to add the league
            OrderResponseModel orderModel = restTemplate.postForObject(url, requestEntity, OrderResponseModel.class);

            return orderModel;
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public void deleteOrder(String orderId, String customerId) {
        try {
            String url = ORDER_SERVICE_BASE_URL.replace("{customerId}", customerId) + "/" + orderId;
            restTemplate.delete(url);
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public List<OrderResponseModel> getAllOrder(String customerId){
        try {
            String url = ORDER_SERVICE_BASE_URL.replace("{customerId}", customerId);
            OrderResponseModel[] orderResponseModels = restTemplate.getForObject(url, OrderResponseModel[].class);
            return Arrays.asList(orderResponseModels);
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }


    public OrderResponseModel getOrderByOrderId(String orderId, String customerId){
        try {
            String url= ORDER_SERVICE_BASE_URL.replace("{customerId}", customerId)  + "/" + orderId;
            OrderResponseModel orderModel =  restTemplate.getForObject(url,OrderResponseModel.class);
            return orderModel;
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

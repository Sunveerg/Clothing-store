package com.example.apigateway.presentationlayer.order;

import com.example.apigateway.domainclientlayer.order.Status;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class OrderControllerIntegrationTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    private MockRestServiceServer mockRestServiceServer;

    @BeforeEach
    public void init() {
        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void getOrders_ShouldReturnAllOrders() throws JsonProcessingException {
        // Prepare mock response
        List<OrderResponseModel> transferResponseModels = Collections.singletonList(
                OrderResponseModel.builder()
                        .orderId("1")
                        .orderDate("test date")
                        .status(Status.DELIVERED)
                        .employeeId("1")
                        .productId("1")
                        .customerId("1")
                        .firstName("john")
                        .lastName("doe")
                        .commissionRate(3.6)
                        .name("department1")
                        .headCount(1)
                        .productName("product")
                        .productStock(1)
                        .build()
        );
        mockRestServiceServer.expect(requestTo("http://localhost:8080/api/v1/customers/c3540a89-cb47-4c96-888e-ff96708db4d8/orders"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(transferResponseModels))
                );

        // Make request and verify response
        webTestClient.get()
                .uri("/api/v1/customers/c3540a89-cb47-4c96-888e-ff96708db4d8/orders")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(OrderResponseModel.class)
                .value(response -> {
                    assertEquals(response.size(), 1);
                    assertThat(response.get(0), samePropertyValuesAs(transferResponseModels.get(0)));
                });
    }


    @Test
    void whenGoodOrderId_thenReturnOrder() throws JsonProcessingException {
        // Prepare mock response
        OrderResponseModel transferResponseModel = OrderResponseModel.builder()
                .orderId("1")
                .orderDate("test date")
                .status(Status.DELIVERED)
                .employeeId("1")
                .productId("1")
                .customerId("1")
                .firstName("john")
                .lastName("doe")
                .commissionRate(3.6)
                .name("department1")
                .headCount(1)
                .productName("product")
                .productStock(1)
                .build();

        mockRestServiceServer.expect(requestTo("http://localhost:8080/api/v1/customers/c3540a89-cb47-4c96-888e-ff96708db4d8/orders/1"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(transferResponseModel))
                );

        // Make request and verify response
        webTestClient.get()
                .uri("/api/v1/customers/c3540a89-cb47-4c96-888e-ff96708db4d8/orders/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(OrderResponseModel.class)
                .value(response -> assertThat(response, samePropertyValuesAs(transferResponseModel)));
    }


    @Test
    void whenWrongOrderId_thenReturnNotFoundException() throws JsonProcessingException {
        // Prepare mock response
        mockRestServiceServer.expect(requestTo("http://localhost:8080/api/v1/orders/c3540a89-cb47-4c96-888e-ff96708db4d8/12"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON)
                );

        // Make request and verify response
        webTestClient
                .get()
                .uri("/api/v1/orders/c3540a89-cb47-4c96-888e-ff96708db4d8/12")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }
    
}

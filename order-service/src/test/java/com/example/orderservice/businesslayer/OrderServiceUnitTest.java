package com.example.orderservice.businesslayer;

import com.example.orderservice.ordersubdomain.businesslayer.OrderService;
import com.example.orderservice.ordersubdomain.dataacceslayer.Order;
import com.example.orderservice.ordersubdomain.dataacceslayer.OrderRepository;
import com.example.orderservice.ordersubdomain.dataacceslayer.Status;
import com.example.orderservice.ordersubdomain.domainclientlayer.Customer.CustomerModel;
import com.example.orderservice.ordersubdomain.domainclientlayer.Customer.CustomerServiceClient;
import com.example.orderservice.ordersubdomain.domainclientlayer.Employee.EmployeeModel;
import com.example.orderservice.ordersubdomain.domainclientlayer.Employee.EmployeeServiceClient;
import com.example.orderservice.ordersubdomain.domainclientlayer.Product.ProductModel;
import com.example.orderservice.ordersubdomain.domainclientlayer.Product.ProductServiceClient;
import com.example.orderservice.ordersubdomain.mapperlayer.OrderResponseMapper;
import com.example.orderservice.ordersubdomain.presentationlayer.OrderRequestModel;
import com.example.orderservice.ordersubdomain.presentationlayer.OrderResponseModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
//@TestPropertySource(properties = "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration")
@ActiveProfiles("test")
public class OrderServiceUnitTest {



}

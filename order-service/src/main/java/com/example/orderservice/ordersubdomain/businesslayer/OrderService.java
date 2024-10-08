package com.example.orderservice.ordersubdomain.businesslayer;


import com.example.orderservice.ordersubdomain.presentationlayer.OrderRequestModel;
import com.example.orderservice.ordersubdomain.presentationlayer.OrderResponseModel;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


public interface OrderService {

    List<OrderResponseModel> getAllOrder(String customerId);

    OrderResponseModel getOrderById(String customerId, String orderId);

    OrderResponseModel addOrder(OrderRequestModel orderRequestModel, String customerId);

    OrderResponseModel updateOrder(OrderRequestModel orderRequestModel, String orderId, String customerId);

    void deleteOrder(String orderId, String customerId);

}

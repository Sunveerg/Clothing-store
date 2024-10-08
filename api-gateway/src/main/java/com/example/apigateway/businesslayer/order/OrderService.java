package com.example.apigateway.businesslayer.order;




import com.example.apigateway.presentationlayer.order.OrderRequestModel;
import com.example.apigateway.presentationlayer.order.OrderResponseModel;

import java.util.List;


public interface OrderService {

    List<OrderResponseModel> getAllOrder(String customerId);

    OrderResponseModel getOrderById(String customerId, String orderId);

    OrderResponseModel addOrder(OrderRequestModel orderRequestModel, String customerId);

    OrderResponseModel updateOrder(OrderRequestModel orderRequestModel, String orderId, String customerId);

    void deleteOrder(String orderId, String customerId);

}

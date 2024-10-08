package com.example.apigateway.businesslayer.order;





import com.example.apigateway.domainclientlayer.order.OrderServiceClient;
import com.example.apigateway.mapperlayer.order.OrderResponseMapper;
import com.example.apigateway.presentationlayer.order.OrderRequestModel;
import com.example.apigateway.presentationlayer.order.OrderResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService{
    private final OrderServiceClient orderServiceClient;
    private final OrderResponseMapper transferResponseMapper;

    public OrderServiceImpl(OrderServiceClient orderServiceClient, OrderResponseMapper transferResponseMapper) {
        this.orderServiceClient = orderServiceClient;
        this.transferResponseMapper = transferResponseMapper;
    }

    @Override
    public List<OrderResponseModel> getAllOrder(String customerId) {
        return transferResponseMapper.entityListToResponseModelList(orderServiceClient.getAllOrder(customerId));
    }

    @Override
    public OrderResponseModel getOrderById(String transferId, String customerId) {
        return transferResponseMapper.entityToResponseModel(orderServiceClient.getOrderByOrderId(transferId, customerId));
    }

    @Override
    public OrderResponseModel addOrder(OrderRequestModel transferRequestModel, String customerId ) {
        return transferResponseMapper.entityToResponseModel(orderServiceClient.addOrder(transferRequestModel, customerId));
    }

    @Override
    public OrderResponseModel updateOrder(OrderRequestModel transferRequestModel,  String transferId , String customerId) {
        return transferResponseMapper.entityToResponseModel(orderServiceClient.updateOrder(transferId,transferRequestModel, customerId));
    }

    @Override
    public void deleteOrder(String transferId, String customerId) {
        orderServiceClient.deleteOrder(transferId, customerId);
    }
}

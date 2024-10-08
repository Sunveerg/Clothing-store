package com.example.orderservice.ordersubdomain.mapperlayer;


import com.example.orderservice.ordersubdomain.dataacceslayer.Order;
import com.example.orderservice.ordersubdomain.presentationlayer.OrderController;
import com.example.orderservice.ordersubdomain.presentationlayer.OrderResponseModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring")
public interface OrderResponseMapper {
    @Mapping(expression = "java(order.getOrderIdentifier().getOrderId())", target = "orderId")
    @Mapping(expression = "java(order.getOrderDate())", target = "orderDate")
    @Mapping(expression = "java(order.getStatus())", target = "status")
    @Mapping(expression = "java(order.getCustomerModel().getCustomerId())", target = "customerId")
    @Mapping(expression = "java(order.getCustomerModel().getFirstName())", target = "firstName")
    @Mapping(expression = "java(order.getCustomerModel().getLastName())", target = "lastName")
    @Mapping(expression = "java(order.getEmployeeModel().getEmployeeId())", target = "employeeId")
    @Mapping(expression = "java(order.getEmployeeModel().getCommissionRate())", target = "commissionRate")
    @Mapping(expression = "java(order.getProductModel().getProductId())", target = "productId")
    @Mapping(expression = "java(order.getProductModel().getProductName())", target = "productName")
    @Mapping(expression = "java(order.getProductModel().getProductStock())", target = "productStock")

    OrderResponseModel entityToResponseModel(Order order);

    List<OrderResponseModel> entityListToResponseModelList(List<Order> order);

}

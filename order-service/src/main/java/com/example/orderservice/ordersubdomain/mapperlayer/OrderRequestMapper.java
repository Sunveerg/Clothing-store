package com.example.orderservice.ordersubdomain.mapperlayer;


import com.example.orderservice.ordersubdomain.dataacceslayer.Order;
import com.example.orderservice.ordersubdomain.dataacceslayer.OrderIdentifier;
import com.example.orderservice.ordersubdomain.domainclientlayer.Customer.CustomerModel;
import com.example.orderservice.ordersubdomain.domainclientlayer.Employee.EmployeeModel;
import com.example.orderservice.ordersubdomain.domainclientlayer.Product.ProductModel;
import com.example.orderservice.ordersubdomain.presentationlayer.OrderRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface OrderRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(expression = "java(orderIdentifier)", target = "orderIdentifier")
    Order requestModelToEntity(OrderRequestModel orderRequestModel,
                               OrderIdentifier orderIdentifier,
                               ProductModel productModel,
                               EmployeeModel employeeModel,
                               CustomerModel customerModel
    );
}

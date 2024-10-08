package com.example.apigateway.mapperlayer.order;





import com.example.apigateway.presentationlayer.customer.CustomerResponseModel;
import com.example.apigateway.presentationlayer.order.OrderController;
import com.example.apigateway.presentationlayer.order.OrderResponseModel;
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


    OrderResponseModel entityToResponseModel(OrderResponseModel order);

    List<OrderResponseModel> entityListToResponseModelList(List<OrderResponseModel> orders);

    @AfterMapping
    default void addLinks(@MappingTarget OrderResponseModel orderResponseMapper, CustomerResponseModel customerResponseModel){
        Link allLink = linkTo(methodOn(OrderController.class).getAllOrders(customerResponseModel.getCustomerId()))
                .withRel("all transfer");
        orderResponseMapper.add(allLink);
    }

}

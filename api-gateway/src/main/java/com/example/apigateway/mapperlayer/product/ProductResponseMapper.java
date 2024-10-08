package com.example.apigateway.mapperlayer.product;


import com.example.apigateway.presentationlayer.product.ProductController;
import com.example.apigateway.presentationlayer.product.ProductResponseModel;
import org.mapstruct.*;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring")
public interface ProductResponseMapper {


    ProductResponseModel entityToResponseModel(ProductResponseModel product);

    List<ProductResponseModel> entityListToResponseModelList(List<ProductResponseModel> products);

    @AfterMapping
    default void addLinks(@MappingTarget ProductResponseModel model) {
        Link selfLink = linkTo(methodOn(ProductController.class).
                getProductById(model.getProductId())).withSelfRel();
        model.add(selfLink);
    }
}

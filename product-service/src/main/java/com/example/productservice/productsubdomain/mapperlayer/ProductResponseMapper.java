package com.example.productservice.productsubdomain.mapperlayer;

import com.example.productservice.productsubdomain.dataaccesslayer.Product;
import com.example.productservice.productsubdomain.presentationlayer.ProductController;
import com.example.productservice.productsubdomain.presentationlayer.ProductResponseModel;
import org.mapstruct.*;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring")
public interface ProductResponseMapper {

    @Mapping(expression = "java(product.getProductIdentifier().getProductId())", target = "productId")
    ProductResponseModel entityToResponseModel(Product product);

    List<ProductResponseModel> entityListToResponseModelList(List<Product> product);

    @AfterMapping
    default void addLinks(@MappingTarget ProductResponseModel model, Product product) {
        Link selfLink = linkTo(methodOn(ProductController.class).
                getProductById(model.getProductId())).withSelfRel();
        model.add(selfLink);
    }
}

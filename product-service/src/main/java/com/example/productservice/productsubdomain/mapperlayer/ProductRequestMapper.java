package com.example.productservice.productsubdomain.mapperlayer;

import com.example.productservice.productsubdomain.dataaccesslayer.Product;
import com.example.productservice.productsubdomain.dataaccesslayer.ProductIdentifier;
import com.example.productservice.productsubdomain.presentationlayer.ProductRequestModel;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring")
public interface ProductRequestMapper {


    @Mappings({
            @Mapping(target = "id", ignore = true),
    })
    Product requestModelToEntity(ProductRequestModel productRequestModel, ProductIdentifier productIdentifier);
}

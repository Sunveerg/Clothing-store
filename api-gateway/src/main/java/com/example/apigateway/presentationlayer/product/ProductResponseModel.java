package com.example.apigateway.presentationlayer.product;


import com.example.apigateway.domainclientlayer.product.Brand;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductResponseModel extends RepresentationModel<ProductResponseModel> {

    String productId;
    String productName;
    String category;
    Brand brand;
    Double price;
    String productDescription;
    Integer stock;

}

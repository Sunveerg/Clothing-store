package com.example.apigateway.presentationlayer.product;


import com.example.apigateway.domainclientlayer.product.Brand;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestModel {

    String productName;
    String category;
    Brand brand;
    Double price;
    String productDescription;
    Integer stock;

}



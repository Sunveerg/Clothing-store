package com.example.productservice.productsubdomain.presentationlayer;

import com.example.productservice.productsubdomain.dataaccesslayer.Brand;
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



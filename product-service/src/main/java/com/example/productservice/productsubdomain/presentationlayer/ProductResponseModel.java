package com.example.productservice.productsubdomain.presentationlayer;

import com.example.productservice.productsubdomain.dataaccesslayer.Brand;
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

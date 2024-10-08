package com.example.productservice.productsubdomain.businesslayer;


import com.example.productservice.productsubdomain.presentationlayer.ProductRequestModel;
import com.example.productservice.productsubdomain.presentationlayer.ProductResponseModel;

import java.util.List;


public interface ProductService {

    List<ProductResponseModel> getAllProduct();

    ProductResponseModel getProductById(String productId);

    ProductResponseModel addProduct(ProductRequestModel productRequestModel);

    ProductResponseModel updateProduct(ProductRequestModel productRequestModel, String productId);

    void deleteProduct(String productId);

}

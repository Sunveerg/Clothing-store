package com.example.apigateway.businesslayer.product;



import com.example.apigateway.presentationlayer.product.ProductRequestModel;
import com.example.apigateway.presentationlayer.product.ProductResponseModel;

import java.util.List;


public interface ProductService {

    List<ProductResponseModel> getAllProduct();

    ProductResponseModel getProductById(String productId);

    ProductResponseModel addProduct(ProductRequestModel productRequestModel);

    ProductResponseModel updateProduct(ProductRequestModel productRequestModel, String productId);

    void deleteProduct(String productId);

}

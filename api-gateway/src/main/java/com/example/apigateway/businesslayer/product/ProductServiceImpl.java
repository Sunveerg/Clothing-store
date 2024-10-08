package com.example.apigateway.businesslayer.product;

import com.example.apigateway.domainclientlayer.product.ProductServiceClient;
import com.example.apigateway.mapperlayer.product.ProductResponseMapper;
import com.example.apigateway.presentationlayer.product.ProductRequestModel;
import com.example.apigateway.presentationlayer.product.ProductResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService{

    private final ProductResponseMapper productResponseMapper;
    private final ProductServiceClient productServiceClient;

    public ProductServiceImpl(ProductResponseMapper productResponseMapper, ProductServiceClient productServiceClient) {
        this.productResponseMapper = productResponseMapper;
        this.productServiceClient = productServiceClient;
    }

    @Override
    public List<ProductResponseModel> getAllProduct() {
        return productResponseMapper.entityListToResponseModelList(productServiceClient.getAllProducts());
    }

    @Override
    public ProductResponseModel getProductById(String productId) {
        return productResponseMapper.entityToResponseModel(productServiceClient.getProductByProductId(productId));
    }

    @Override
    public void deleteProduct(String productId) {
        productServiceClient.removeProduct(productId);
    }

    @Override
    public ProductResponseModel addProduct(ProductRequestModel productRequestModel) {
        return  productResponseMapper.entityToResponseModel(productServiceClient.addProduct(productRequestModel));
    }

    @Override
    public ProductResponseModel updateProduct(ProductRequestModel productRequestModel, String productId) {
        return productResponseMapper.entityToResponseModel(productServiceClient.updateProduct(productId,productRequestModel));
    }
    
}

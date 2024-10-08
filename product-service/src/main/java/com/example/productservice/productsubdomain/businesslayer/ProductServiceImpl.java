package com.example.productservice.productsubdomain.businesslayer;


import com.example.productservice.productsubdomain.dataaccesslayer.Product;
import com.example.productservice.productsubdomain.dataaccesslayer.ProductIdentifier;
import com.example.productservice.productsubdomain.dataaccesslayer.ProductRepository;
import com.example.productservice.productsubdomain.mapperlayer.ProductRequestMapper;
import com.example.productservice.productsubdomain.mapperlayer.ProductResponseMapper;
import com.example.productservice.productsubdomain.presentationlayer.ProductRequestModel;
import com.example.productservice.productsubdomain.presentationlayer.ProductResponseModel;
import com.example.productservice.utils.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ProductResponseMapper productResponseMapper;
    private final ProductRequestMapper productRequestMapper;


    public ProductServiceImpl(ProductRepository productRepository, ProductResponseMapper productResponseMapper, ProductRequestMapper productRequestMapper) {
        this.productRepository = productRepository;
        this.productResponseMapper = productResponseMapper;
        this.productRequestMapper = productRequestMapper;
    }

    @Override
    public List<ProductResponseModel> getAllProduct() {

        List<Product> products = productRepository.findAll();

        return productResponseMapper.entityListToResponseModelList(products);
    }

    @Override
    public ProductResponseModel getProductById(String productId) {
        Product product = productRepository.findByProductIdentifier_productId(productId);
        if(product == null){
            throw new NotFoundException("Unknown productId " + productId);
        }
        return productResponseMapper.entityToResponseModel(product);
    }

    @Override
    public ProductResponseModel addProduct(ProductRequestModel productRequestModel) {
        Product product = productRequestMapper.requestModelToEntity(productRequestModel, new ProductIdentifier());

        return productResponseMapper.entityToResponseModel(productRepository.save(product));
    }

    @Override
    public ProductResponseModel updateProduct(ProductRequestModel productRequestModel, String productId) {
        Product existingProduct = productRepository.findByProductIdentifier_productId(productId);

        if(existingProduct == null){
            throw new NotFoundException("Unknown productId " + productId);
        }

        Product product = productRequestMapper.requestModelToEntity(productRequestModel, existingProduct.getProductIdentifier());
        product.setId(existingProduct.getId());

        return productResponseMapper.entityToResponseModel(productRepository.save(product));

    }

    @Override
    public void deleteProduct(String productId) {
        Product existingProduct = productRepository.findByProductIdentifier_productId(productId);
        if (existingProduct == null){
            throw new NotFoundException("Unknown productId " + productId);
        }
        productRepository.delete(existingProduct);
    }
}

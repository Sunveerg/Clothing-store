package com.example.productservice.productsubdomain.presentationlayer;

import com.example.productservice.productsubdomain.businesslayer.ProductService;
import com.example.productservice.utils.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<ProductResponseModel>> getAllProducts(){

        return ResponseEntity.ok().body(productService.getAllProduct());

    }

    @GetMapping(value = "/{productId}", produces = "application/json")
    public ResponseEntity<ProductResponseModel> getProductById(@PathVariable String productId) {
            ProductResponseModel product = productService.getProductById(productId);
            return ResponseEntity.ok(product);

    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<ProductResponseModel> addProduct(@RequestBody ProductRequestModel productRequestModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.addProduct(productRequestModel));
    }

    @PutMapping(value = "/{productId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ProductResponseModel> updateProduct(@RequestBody ProductRequestModel productRequestModel, @PathVariable String productId){
        return ResponseEntity.ok().body(productService.updateProduct(productRequestModel, productId));
    }

    @DeleteMapping(value = "{productId}")
    public ResponseEntity<Void> deleteproduct(@PathVariable String productId){
        productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

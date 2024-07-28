package com.nikhil.order_management.controller;

import com.nikhil.order_management.entity.Product;
import com.nikhil.order_management.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer productId){
        return productService.getProductById(productId);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody Product product){
        return productService.addProduct(product);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer productId){
        return productService.deleteProduct(productId);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable Integer productId, @RequestBody Product product){
        product.setProductId(productId);
        return productService.updateProduct(product);
    }

}

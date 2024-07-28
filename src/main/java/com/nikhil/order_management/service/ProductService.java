package com.nikhil.order_management.service;

import com.nikhil.order_management.entity.Product;
import com.nikhil.order_management.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<Product> getProductById(Integer productId) {
        Optional<Product> existingProduct = productRepository.findById(productId);
        return existingProduct.map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<String> addProduct(Product product) {
        String normalizedProductName = product.getProductName().toLowerCase();
        Optional<Product> existingProduct = productRepository.findAll()
                        .stream()
                        .filter(p -> p.getProductName().toLowerCase().equals(normalizedProductName))
                        .findFirst();
        if (existingProduct.isPresent()) {
            return new ResponseEntity<>("Product already exists", HttpStatus.BAD_REQUEST);
        }
        productRepository.save(product);
        return new ResponseEntity<>("Product has been added", HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteProduct(Integer productId){
        Optional<Product> existingProduct = productRepository.findById(productId);
        if(existingProduct.isPresent()){
            productRepository.deleteById(productId);
            return new ResponseEntity<>("Product deleted", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("Product does not exist", HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<String> updateProduct(Product product) {
        Optional<Product> existingProduct = productRepository.findById(product.getProductId());
        if(existingProduct.isPresent()){
            Product updatedProduct = existingProduct.get();
            updatedProduct.setProductName(product.getProductName());
            updatedProduct.setDescription(product.getDescription());
            updatedProduct.setPrice(product.getPrice());
            updatedProduct.setQuantityAvailable(product.getQuantityAvailable());
            updatedProduct.setCategory(product.getCategory());

            productRepository.save(updatedProduct);
            return new ResponseEntity<>("Product information has been updated", HttpStatus.OK);
        }
        return new ResponseEntity<>("Product does not exist", HttpStatus.NOT_FOUND);
    }
}

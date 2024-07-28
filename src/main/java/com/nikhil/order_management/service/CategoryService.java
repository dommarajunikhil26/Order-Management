package com.nikhil.order_management.service;

import com.nikhil.order_management.entity.Category;
import com.nikhil.order_management.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public ResponseEntity<Category> getCategory(Integer categoryId){
        Optional<Category> existingCategory = categoryRepository.findById(categoryId);
        return existingCategory.map(category -> new ResponseEntity<>(category, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<String> addCategory(Category category) {
        String normalizedCategoryName = category.getCategoryName().toLowerCase();
        Optional<Category> existingCategory = categoryRepository.findAll()
                .stream()
                .filter(category1 -> category1.getCategoryName().toLowerCase().equals(normalizedCategoryName))
                .findFirst();
        if(existingCategory.isEmpty()){
            categoryRepository.save(category);
            return new ResponseEntity<>("Category has been added",HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Category already exists", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteCategory(Integer categoryId) {
        Optional<Category> existingCategory = categoryRepository.findById(categoryId);
        if(existingCategory.isPresent()){
            categoryRepository.deleteById(categoryId);
            return new ResponseEntity<>("Category has been deleted", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("Category does not exist", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> updateCategory(Category category) {
        Optional<Category> existingCategory = categoryRepository.findById(category.getCategoryId());
        if(existingCategory.isPresent()){
            Category updatedCategory = existingCategory.get();
            updatedCategory.setCategoryName(category.getCategoryName());
            updatedCategory.setCategoryDescription(category.getCategoryDescription());

            categoryRepository.save(updatedCategory);
            return new ResponseEntity<>("Category has been updated", HttpStatus.OK);
        }
        return new ResponseEntity<>("Category does not exist", HttpStatus.BAD_REQUEST);

    }
}

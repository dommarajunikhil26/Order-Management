package com.nikhil.order_management.controller;

import com.nikhil.order_management.entity.Category;
import com.nikhil.order_management.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("{categoryId}")
    public ResponseEntity<Category> getCategory(@PathVariable Integer categoryId){
        return categoryService.getCategory(categoryId);
    }

    @PostMapping("add")
    public ResponseEntity<String> addCategory(@RequestBody Category category){
        return categoryService.addCategory(category);
    }

    @DeleteMapping("{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Integer categoryId){
        return categoryService.deleteCategory(categoryId);
    }

    @PutMapping("{categoryId}")
    public ResponseEntity<String> updateCategory(@PathVariable Integer categoryId, @RequestBody Category category){
        category.setCategoryId(categoryId);
        return categoryService.updateCategory(category);
    }
}

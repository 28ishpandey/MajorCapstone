package com.food.restaurant.controller;

import com.food.restaurant.dto.CategoryCreateDTO;
import com.food.restaurant.dto.CategoryResponseDTO;
import com.food.restaurant.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<String> createCategory(@RequestBody CategoryCreateDTO categoryCreateDTO) {
        try{
            return categoryService.createCategory(categoryCreateDTO);
        } catch (Exception e) {
            log.error("Error creating category: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<String> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryCreateDTO categoryCreateDTO) {
        try {
            return categoryService.updateCategory(categoryId, categoryCreateDTO);
        } catch (Exception e) {
            log.error("Error updating category: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        try {
            return categoryService.deleteCategory(categoryId);
        } catch (Exception e) {
            log.error("Error deleting category: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long categoryId) {
        try {
            return categoryService.getCategoryById(categoryId);
        } catch (Exception e) {
            log.error("Error retrieving category: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
        try {
            return categoryService.getAllCategories();
        } catch (Exception e) {
            log.error("Error retrieving categories: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


package com.food.restaurant.service;

import com.food.restaurant.constant.ResponseConstants;
import com.food.restaurant.dto.CategoryCreateDTO;
import com.food.restaurant.dto.CategoryResponseDTO;
import com.food.restaurant.entity.Category;
import com.food.restaurant.error.CustomException;
import com.food.restaurant.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public ResponseEntity<String> createCategory(CategoryCreateDTO categoryCreateDTO) {
        Category category = new Category();
        category.setRestaurantId(categoryCreateDTO.getRestaurantId());
        category.setName(categoryCreateDTO.getName());

        categoryRepository.save(category);
        log.info("Category created: {}", category.getName());

        return new ResponseEntity<>(ResponseConstants.CATEGORY_CREATED, HttpStatus.CREATED);
    }

    public ResponseEntity<String> updateCategory(Long categoryId, CategoryCreateDTO categoryCreateDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(ResponseConstants.CATEGORY_NOT_FOUND, HttpStatus.NOT_FOUND.value()));

        category.setName(categoryCreateDTO.getName());
        categoryRepository.save(category);

        log.info("Category updated: {}", category.getName());
        return new ResponseEntity<>(ResponseConstants.CATEGORY_UPDATED, HttpStatus.OK);
    }

    public ResponseEntity<String> deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(ResponseConstants.CATEGORY_NOT_FOUND, HttpStatus.NOT_FOUND.value()));

        categoryRepository.delete(category);
        log.info("Category deleted: {}", category.getName());

        return new ResponseEntity<>(ResponseConstants.CATEGORY_DELETED, HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<CategoryResponseDTO> getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(ResponseConstants.CATEGORY_NOT_FOUND, HttpStatus.NOT_FOUND.value()));

        CategoryResponseDTO response = convertToResponseDTO(category);
        log.info("Category retrieved: {}", category.getName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryResponseDTO> response = categories.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());

        log.info("All categories retrieved");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private CategoryResponseDTO convertToResponseDTO(Category category) {
        CategoryResponseDTO response = new CategoryResponseDTO();
        response.setCategoryId(category.getCategoryId());
        response.setRestaurantId(category.getRestaurantId());
        response.setName(category.getName());
        return response;
    }

}

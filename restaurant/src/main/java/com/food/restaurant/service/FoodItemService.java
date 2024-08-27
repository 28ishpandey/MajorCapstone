package com.food.restaurant.service;

import com.food.restaurant.constant.ResponseConstants;
import com.food.restaurant.dto.FoodItemCreateDTO;
import com.food.restaurant.dto.FoodItemResponseDTO;

import com.food.restaurant.entity.FoodItem;
import com.food.restaurant.error.CustomException;

import com.food.restaurant.repository.FoodItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FoodItemService {

    @Autowired
    private FoodItemRepository foodItemRepository;

    public ResponseEntity<String> createFoodItem(FoodItemCreateDTO foodItemCreateDTO) {
        FoodItem foodItem = new FoodItem();
        foodItem.setCategoryId(foodItemCreateDTO.getCategoryId());
        foodItem.setRestaurantId(foodItemCreateDTO.getRestaurantId());
        foodItem.setName(foodItemCreateDTO.getName());
        foodItem.setPrice(foodItemCreateDTO.getPrice());
        foodItem.setAvailability(foodItemCreateDTO.getAvailability());
        foodItem.setIsVeg(foodItemCreateDTO.getIsVeg());

        foodItemRepository.save(foodItem);
        log.info("Food item created: {}", foodItem.getName());

        return new ResponseEntity<>(ResponseConstants.FOOD_ITEM_CREATED, HttpStatus.CREATED);
    }

    public ResponseEntity<String> updateFoodItem(Long foodItemId, FoodItemCreateDTO foodItemCreateDTO) {
        FoodItem foodItem = foodItemRepository.findById(foodItemId)
                .orElseThrow(() -> new CustomException(ResponseConstants.FOOD_ITEM_NOT_FOUND, HttpStatus.NOT_FOUND.value()));

        foodItem.setName(foodItemCreateDTO.getName());
        foodItem.setPrice(foodItemCreateDTO.getPrice());
        foodItem.setAvailability(foodItemCreateDTO.getAvailability());
        foodItem.setIsVeg(foodItemCreateDTO.getIsVeg());

        foodItemRepository.save(foodItem);
        log.info("Food item updated: {}", foodItem.getName());

        return new ResponseEntity<>(ResponseConstants.FOOD_ITEM_UPDATED, HttpStatus.OK);
    }

    public ResponseEntity<String> deleteFoodItem(Long foodItemId) {
        FoodItem foodItem = foodItemRepository.findById(foodItemId)
                .orElseThrow(() -> new CustomException(ResponseConstants.FOOD_ITEM_NOT_FOUND, HttpStatus.NOT_FOUND.value()));

        foodItemRepository.delete(foodItem);
        log.info("Food item deleted: {}", foodItem.getName());

        return new ResponseEntity<>(ResponseConstants.FOOD_ITEM_DELETED, HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<FoodItemResponseDTO> getFoodItemById(Long foodItemId) {
        FoodItem foodItem = foodItemRepository.findById(foodItemId)
                .orElseThrow(() -> new CustomException(ResponseConstants.FOOD_ITEM_NOT_FOUND, HttpStatus.NOT_FOUND.value()));

        FoodItemResponseDTO response = convertToResponseDTO(foodItem);
        log.info("Food item retrieved: {}", foodItem.getName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<List<FoodItemResponseDTO>> getAllFoodItems() {
        List<FoodItem> foodItems = foodItemRepository.findAll();
        List<FoodItemResponseDTO> response = foodItems.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());

        log.info("All food items retrieved");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private FoodItemResponseDTO convertToResponseDTO(FoodItem foodItem) {
        FoodItemResponseDTO response = new FoodItemResponseDTO();
        response.setId(foodItem.getId());
        response.setCategoryId(foodItem.getCategoryId());
        response.setRestaurantId(foodItem.getRestaurantId());
        response.setName(foodItem.getName());
        response.setPrice(foodItem.getPrice());
        response.setAvailability(foodItem.getAvailability());
        response.setIsVeg(foodItem.getIsVeg());
        return response;
    }
}

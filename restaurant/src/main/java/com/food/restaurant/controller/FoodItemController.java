package com.food.restaurant.controller;

import com.food.restaurant.dto.FoodItemCreateDTO;
import com.food.restaurant.dto.FoodItemResponseDTO;
import com.food.restaurant.service.FoodItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/food-items")
public class FoodItemController {

    @Autowired
    private FoodItemService foodItemService;

    @PostMapping
    public ResponseEntity<String> createFoodItem(@RequestBody FoodItemCreateDTO foodItemCreateDTO) {
        try {
            return foodItemService.createFoodItem(foodItemCreateDTO);
        } catch (Exception e) {
            log.error("Error creating food item: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{foodItemId}")
    public ResponseEntity<String> updateFoodItem(@PathVariable Long foodItemId, @RequestBody FoodItemCreateDTO foodItemCreateDTO) {
        try {
            return foodItemService.updateFoodItem(foodItemId, foodItemCreateDTO);
        } catch (Exception e) {
            log.error("Error updating food item: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{foodItemId}")
    public ResponseEntity<String> deleteFoodItem(@PathVariable Long foodItemId) {
        try {
            return foodItemService.deleteFoodItem(foodItemId);
        } catch (Exception e) {
            log.error("Error deleting food item: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{foodItemId}")
    public ResponseEntity<FoodItemResponseDTO> getFoodItemById(@PathVariable Long foodItemId) {
        try {
            return foodItemService.getFoodItemById(foodItemId);
        } catch (Exception e) {
            log.error("Error retrieving food item: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<FoodItemResponseDTO>> getAllFoodItems() {
        try {
            return foodItemService.getAllFoodItems();
        } catch (Exception e) {
            log.error("Error retrieving food items: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


package com.food.restaurant.controller;

import com.food.restaurant.dto.RestaurantCreateDTO;
import com.food.restaurant.dto.RestaurantResponseDTO;
import com.food.restaurant.service.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<String> createRestaurant(@RequestBody RestaurantCreateDTO restaurantCreateDTO) {
        try {
            return restaurantService.createRestaurant(restaurantCreateDTO);
        } catch (Exception e) {
            log.error("Error creating restaurant: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<String> updateRestaurant(@PathVariable Long restaurantId, @RequestBody RestaurantCreateDTO restaurantCreateDTO) {
        try {
            return restaurantService.updateRestaurant(restaurantId, restaurantCreateDTO);
        } catch (Exception e) {
            log.error("Error updating restaurant: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable Long restaurantId) {
        try {
            return restaurantService.deleteRestaurant(restaurantId);
        } catch (Exception e) {
            log.error("Error deleting restaurant: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResponseDTO> getRestaurantById(@PathVariable Long restaurantId) {
        try {
            return restaurantService.getRestaurantById(restaurantId);
        } catch (Exception e) {
            log.error("Error retrieving restaurant: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<RestaurantResponseDTO>> getAllRestaurants() {
        try {
            return restaurantService.getAllRestaurants();
        } catch (Exception e) {
            log.error("Error retrieving restaurants: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
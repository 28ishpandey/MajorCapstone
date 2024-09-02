package com.food.restaurant.controller;

import com.food.restaurant.dto.LoginDTO;
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
        log.info("Registering user with email: {}", restaurantCreateDTO.getEmail());
        return restaurantService.createRestaurant(restaurantCreateDTO);

    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<String> updateRestaurant(@PathVariable Long restaurantId, @RequestBody RestaurantCreateDTO restaurantCreateDTO) {
        return restaurantService.updateRestaurant(restaurantId, restaurantCreateDTO);

    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable Long restaurantId) {
        return restaurantService.deleteRestaurant(restaurantId);

    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResponseDTO> getRestaurantById(@PathVariable Long restaurantId) {
        return restaurantService.getRestaurantById(restaurantId);
    }

    @GetMapping
    public ResponseEntity<List<RestaurantResponseDTO>> getAllRestaurants() {
            return restaurantService.getAllRestaurants();

    }
    @PostMapping("/login")
    public ResponseEntity<RestaurantResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        log.info("Login attempt with email: {}", loginDTO.getEmail());
        RestaurantResponseDTO responseDTO = restaurantService.login(loginDTO);
        log.info("Login successful");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
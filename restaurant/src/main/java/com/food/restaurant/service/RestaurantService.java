package com.food.restaurant.service;

import com.food.restaurant.constant.ResponseConstants;
import com.food.restaurant.dto.RestaurantCreateDTO;
import com.food.restaurant.dto.RestaurantResponseDTO;
import com.food.restaurant.entity.Restaurant;
import com.food.restaurant.error.CustomException;
import com.food.restaurant.repository.RestaurantRepository;
import com.food.restaurant.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    public ResponseEntity<String> createRestaurant(RestaurantCreateDTO restaurantCreateDTO){
        Restaurant restaurant = new Restaurant();
        restaurant.setEmail(restaurantCreateDTO.getEmail());
        restaurant.setContactNumber(restaurantCreateDTO.getContactNumber());

        String encryptedPassword = PasswordUtil.encryptPassword(restaurantCreateDTO.getPassword());
        restaurant.setPassword(encryptedPassword);
        restaurant.setName(restaurantCreateDTO.getName());
        restaurant.setAddress(restaurantCreateDTO.getAddress());
        restaurantRepository.save(restaurant);
        log.info("Restaurant created: {}", restaurant.getName());
        return new ResponseEntity<>(ResponseConstants.RESTAURANT_CREATED, HttpStatus.CREATED);
    }

    public ResponseEntity<String> updateRestaurant(Long restaurantId,RestaurantCreateDTO restaurantCreateDTO){
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(()-> new CustomException(ResponseConstants.RESTAURANT_NOT_FOUND,HttpStatus.NOT_FOUND.value()));
        restaurant.setEmail(restaurantCreateDTO.getEmail());
        restaurant.setContactNumber(restaurantCreateDTO.getContactNumber());
        restaurant.setPassword(restaurantCreateDTO.getPassword());
        restaurant.setName(restaurantCreateDTO.getName());
        restaurant.setAddress(restaurantCreateDTO.getAddress());
        restaurantRepository.save(restaurant);
        log.info("Restaurant updated: {}", restaurant.getName());

        return new ResponseEntity<>(ResponseConstants.RESTAURANT_UPDATED, HttpStatus.OK);
    }

    public ResponseEntity<String> deleteRestaurant(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new CustomException(ResponseConstants.RESTAURANT_NOT_FOUND, HttpStatus.NOT_FOUND.value()));

        restaurantRepository.delete(restaurant);
        log.info("Restaurant deleted: {}", restaurant.getName());

        return new ResponseEntity<>(ResponseConstants.RESTAURANT_DELETED, HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<RestaurantResponseDTO> getRestaurantById(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new CustomException(ResponseConstants.RESTAURANT_NOT_FOUND, HttpStatus.NOT_FOUND.value()));

        RestaurantResponseDTO response = convertToResponseDTO(restaurant);
        log.info("Restaurant retrieved: {}", restaurant.getName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<List<RestaurantResponseDTO>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        List<RestaurantResponseDTO> response = restaurants.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());

        log.info("All restaurants retrieved");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private RestaurantResponseDTO convertToResponseDTO(Restaurant restaurant) {
        RestaurantResponseDTO response = new RestaurantResponseDTO();
        response.setRestaurantId(restaurant.getRestaurantId());
        response.setEmail(restaurant.getEmail());
        response.setContactNumber(restaurant.getContactNumber());
        response.setName(restaurant.getName());
        response.setAddress(restaurant.getAddress());
        return response;
    }
}

package com.food.restaurant.dto;

import lombok.Data;

@Data
public class RestaurantResponseDTO {
    private Long restaurantId;
    private String email;
    private String contactNumber;
    private String name;
    private String address;
}

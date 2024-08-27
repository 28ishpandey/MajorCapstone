package com.food.restaurant.dto;

import lombok.Data;

@Data
public class FoodItemResponseDTO {
    private Long id;
    private Long categoryId;
    private Long restaurantId;
    private String name;
    private Double price;
    private Boolean availability;
    private Boolean isVeg;
}

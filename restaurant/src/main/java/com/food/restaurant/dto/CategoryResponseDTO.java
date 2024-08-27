package com.food.restaurant.dto;

import lombok.Data;

@Data
public class CategoryResponseDTO {
    private Long categoryId;
    private Long restaurantId;
    private String name;
}

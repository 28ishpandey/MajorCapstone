package com.food.restaurant.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class FoodItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long categoryId;
    private Long restaurantId;
    private String name;
    private Double price;
    private Boolean availability;
    private Boolean isVeg;

}

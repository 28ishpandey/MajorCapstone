package com.food.restaurant.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private Long restaurantId;
    private String name;
}


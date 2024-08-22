package com.food.restaurant.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data
@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantId;
    private String email;
    private String contactNumber;
    private String password;
    private String name;
    private String address;

}


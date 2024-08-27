package com.food.restaurant.dto;

import lombok.Data;

@Data
public class RestaurantCreateDTO {
    private String email;
    private String contactNumber;
    private String password;
    private String name;
    private String address;
}

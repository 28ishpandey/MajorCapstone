package com.food.user.dto;

import lombok.Data;

@Data
public class AddressCreateDTO {
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private Long userId;
}

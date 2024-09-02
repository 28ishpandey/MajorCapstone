package com.food.user.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for creating a new address.
 * Contains information required to create a new address record.
 */
@Data
public class AddressCreateDTO {

    /**
     * Title or name of the address (e.g., 'Home', 'Office').
     */
    private String addressTitle;

    /**
     * Street name and number of the address.
     */
    private String street;

    /**
     * City where the address is located.
     */
    private String city;

    /**
     * State or region where the address is located.
     */
    private String state;

    /**
     * Postal code for the address.
     */
    private String postalCode;

    /**
     * Country where the address is located.
     */
    private String country;

    /**
     * Identifier of the user associated with this address.
     */
    private Long userId;
}
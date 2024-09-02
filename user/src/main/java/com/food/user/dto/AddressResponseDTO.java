package com.food.user.dto;

import lombok.Data;
/**
 * Data Transfer Object (DTO) for representing an address in responses.
 * Contains details of an address returned from the server.
 */
@Data
public class AddressResponseDTO {

    /**
     * Unique identifier of the address.
     */
    private Long addressId;

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


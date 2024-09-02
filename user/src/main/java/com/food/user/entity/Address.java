package com.food.user.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Entity representing an address in the system.
 * Maps to the 'Address' table in the database.
 */
@Data
@Entity
public class Address {

    /**
     * Unique identifier of the address. Auto-generated by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    /**
     * Title or name of the address (e.g., 'Home', 'Office'). Cannot be null.
     */
    @Column(nullable = false)
    private String addressTitle;

    /**
     * Street name and number of the address. Cannot be null.
     */
    @Column(nullable = false)
    private String street;

    /**
     * City where the address is located. Cannot be null.
     */
    @Column(nullable = false)
    private String city;

    /**
     * State or region where the address is located. Cannot be null.
     */
    @Column(nullable = false)
    private String state;

    /**
     * Postal code for the address. Cannot be null.
     */
    @Column(nullable = false)
    private String postalCode;

    /**
     * Country where the address is located. Cannot be null.
     */
    @Column(nullable = false)
    private String country;

    /**
     * Identifier of the user associated with this address. Cannot be null.
     */
    @Column(nullable = false)
    private Long userId;
}


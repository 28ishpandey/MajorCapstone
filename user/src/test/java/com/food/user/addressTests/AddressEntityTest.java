package com.food.user.addressTests;

import com.food.user.entity.Address;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressEntityTest {
    @Test
    public void testAddressEntity() {
        Address address = new Address();
        address.setAddressId(1L);
        address.setStreet("123 Main St");
        address.setCity("Springfield");
        address.setState("IL");
        address.setPostalCode("62701");
        address.setCountry("USA");
        address.setUserId(1L);

        assertEquals(1L, address.getAddressId());
        assertEquals("123 Main St", address.getStreet());
        assertEquals("Springfield", address.getCity());
        assertEquals("IL", address.getState());
        assertEquals("62701", address.getPostalCode());
        assertEquals("USA", address.getCountry());
        assertEquals(1L, address.getUserId());
    }
}

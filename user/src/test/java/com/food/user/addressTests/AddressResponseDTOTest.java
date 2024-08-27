package com.food.user.addressTests;

import com.food.user.dto.AddressResponseDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressResponseDTOTest {
    @Test
    public void testAddressResponseDTO() {
        AddressResponseDTO addressResponseDTO = new AddressResponseDTO();
        addressResponseDTO.setAddressId(1L);
        addressResponseDTO.setStreet("123 Main St");
        addressResponseDTO.setCity("Springfield");
        addressResponseDTO.setState("IL");
        addressResponseDTO.setPostalCode("62701");
        addressResponseDTO.setCountry("USA");
        addressResponseDTO.setUserId(1L);

        assertEquals(1L, addressResponseDTO.getAddressId());
        assertEquals("123 Main St", addressResponseDTO.getStreet());
        assertEquals("Springfield", addressResponseDTO.getCity());
        assertEquals("IL", addressResponseDTO.getState());
        assertEquals("62701", addressResponseDTO.getPostalCode());
        assertEquals("USA", addressResponseDTO.getCountry());
        assertEquals(1L, addressResponseDTO.getUserId());
    }
}

package com.food.user.addressTests;

import com.food.user.dto.AddressCreateDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressCreateDTOTest {
    @Test
    public void testAddressCreateDTO() {
        AddressCreateDTO addressCreateDTO = new AddressCreateDTO();
        addressCreateDTO.setStreet("123 Main St");
        addressCreateDTO.setCity("Springfield");
        addressCreateDTO.setState("IL");
        addressCreateDTO.setPostalCode("62701");
        addressCreateDTO.setCountry("USA");
        addressCreateDTO.setUserId(1L);

        assertEquals("123 Main St", addressCreateDTO.getStreet());
        assertEquals("Springfield", addressCreateDTO.getCity());
        assertEquals("IL", addressCreateDTO.getState());
        assertEquals("62701", addressCreateDTO.getPostalCode());
        assertEquals("USA", addressCreateDTO.getCountry());
        assertEquals(1L, addressCreateDTO.getUserId());
    }
}

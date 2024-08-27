package com.food.user.addressTests;

import com.food.user.dto.AddressCreateDTO;
import com.food.user.dto.AddressResponseDTO;
import com.food.user.entity.Address;

import com.food.user.error.CustomException;
import com.food.user.repository.AddressRepository;
import com.food.user.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AddressServiceTest {

    @InjectMocks
    private AddressService addressService;

    @Mock
    private AddressRepository addressRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAddress() {
        AddressCreateDTO addressCreateDTO = new AddressCreateDTO();
        addressCreateDTO.setStreet("123 Main St");
        addressCreateDTO.setCity("New York");
        addressCreateDTO.setState("NY");
        addressCreateDTO.setPostalCode("10001");
        addressCreateDTO.setCountry("USA");
        addressCreateDTO.setUserId(1L);

        Address address = new Address();
        address.setAddressId(1L);
        address.setStreet(addressCreateDTO.getStreet());
        address.setCity(addressCreateDTO.getCity());
        address.setState(addressCreateDTO.getState());
        address.setPostalCode(addressCreateDTO.getPostalCode());
        address.setCountry(addressCreateDTO.getCountry());
        address.setUserId(addressCreateDTO.getUserId());

        when(addressRepository.save(any(Address.class))).thenReturn(address);

        AddressResponseDTO result = addressService.createAddress(addressCreateDTO);

        assertEquals(1L, result.getAddressId());
        assertEquals("123 Main St", result.getStreet());
        verify(addressRepository, times(1)).save(any(Address.class));
    }

    @Test
    public void testGetAddressById() {
        Address address = new Address();
        address.setAddressId(1L);
        address.setStreet("123 Main St");
        address.setCity("New York");
        address.setState("NY");
        address.setPostalCode("10001");
        address.setCountry("USA");
        address.setUserId(1L);

        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));

        AddressResponseDTO result = addressService.getAddressById(1L);

        assertEquals(1L, result.getAddressId());
        assertEquals("New York", result.getCity());
        verify(addressRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetAddressByIdNotFound() {
        when(addressRepository.findById(1L)).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> {
            addressService.getAddressById(1L);
        });

        assertEquals("Address not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND.value(), exception.getStatusCode());
        verify(addressRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetAddressesByUserId() {
        Address address1 = new Address();
        address1.setAddressId(1L);
        address1.setStreet("123 Main St");
        address1.setCity("New York");
        address1.setState("NY");
        address1.setPostalCode("10001");
        address1.setCountry("USA");
        address1.setUserId(1L);

        Address address2 = new Address();
        address2.setAddressId(2L);
        address2.setStreet("456 Elm St");
        address2.setCity("Los Angeles");
        address2.setState("CA");
        address2.setPostalCode("90001");
        address2.setCountry("USA");
        address2.setUserId(1L);

        when(addressRepository.findByUserId(1L)).thenReturn(Arrays.asList(address1, address2));

        List<AddressResponseDTO> result = addressService.getAddressesByUserId(1L);

        assertEquals(2, result.size());
        assertEquals("New York", result.get(0).getCity());
        assertEquals("Los Angeles", result.get(1).getCity());
        verify(addressRepository, times(1)).findByUserId(1L);
    }

    @Test
    public void testUpdateAddress() {
        Address address = new Address();
        address.setAddressId(1L);
        address.setStreet("123 Main St");
        address.setCity("New York");
        address.setState("NY");
        address.setPostalCode("10001");
        address.setCountry("USA");
        address.setUserId(1L);

        AddressCreateDTO addressCreateDTO = new AddressCreateDTO();
        addressCreateDTO.setStreet("456 Elm St");
        addressCreateDTO.setCity("Los Angeles");
        addressCreateDTO.setState("CA");
        addressCreateDTO.setPostalCode("90001");
        addressCreateDTO.setCountry("USA");
        addressCreateDTO.setUserId(1L);

        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        when(addressRepository.save(any(Address.class))).thenReturn(address);

        AddressResponseDTO result = addressService.updateAddress(1L, addressCreateDTO);

        assertEquals("Los Angeles", result.getCity());
        assertEquals("456 Elm St", result.getStreet());
        verify(addressRepository, times(1)).findById(1L);
        verify(addressRepository, times(1)).save(any(Address.class));
    }

    @Test
    public void testDeleteAddress() {
        Address address = new Address();
        address.setAddressId(1L);
        address.setStreet("123 Main St");
        address.setCity("New York");
        address.setState("NY");
        address.setPostalCode("10001");
        address.setCountry("USA");
        address.setUserId(1L);

        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));

        addressService.deleteAddress(1L);

        verify(addressRepository, times(1)).findById(1L);
        verify(addressRepository, times(1)).delete(address);
    }
}


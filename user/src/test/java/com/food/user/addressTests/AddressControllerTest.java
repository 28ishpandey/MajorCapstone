package com.food.user.addressTests;

import com.food.user.controller.AddressController;
import com.food.user.dto.AddressCreateDTO;
import com.food.user.dto.AddressResponseDTO;
import com.food.user.error.CustomException;
import com.food.user.service.AddressService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AddressController.class)
public class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService addressService;

    @Test
    public void testCreateAddress_Success() throws Exception {
        AddressCreateDTO addressCreateDTO = new AddressCreateDTO();
        addressCreateDTO.setUserId(1L);
        addressCreateDTO.setStreet("123 Main St");
        addressCreateDTO.setCity("Springfield");
        addressCreateDTO.setState("IL");
        addressCreateDTO.setPostalCode("62701");

        AddressResponseDTO responseDTO = new AddressResponseDTO();
        responseDTO.setAddressId(1L);
        responseDTO.setUserId(1L);
        responseDTO.setStreet("123 Main St");
        responseDTO.setCity("Springfield");
        responseDTO.setState("IL");
        responseDTO.setPostalCode("62701");

        Mockito.when(addressService.createAddress(Mockito.any(AddressCreateDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1,\"street\":\"123 Main St\",\"city\":\"Springfield\",\"state\":\"IL\",\"postalCode\":\"62701\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.addressId").value(1L))
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.street").value("123 Main St"))
                .andExpect(jsonPath("$.city").value("Springfield"))
                .andExpect(jsonPath("$.state").value("IL"))
                .andExpect(jsonPath("$.postalCode").value("62701"));
    }

    @Test
    public void testGetAddressById_Success() throws Exception {
        AddressResponseDTO responseDTO = new AddressResponseDTO();
        responseDTO.setAddressId(1L);
        responseDTO.setUserId(1L);
        responseDTO.setStreet("123 Main St");
        responseDTO.setCity("Springfield");
        responseDTO.setState("IL");
        responseDTO.setPostalCode("62701");

        Mockito.when(addressService.getAddressById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/address/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.addressId").value(1L))
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.street").value("123 Main St"))
                .andExpect(jsonPath("$.city").value("Springfield"))
                .andExpect(jsonPath("$.state").value("IL"))
                .andExpect(jsonPath("$.postalCode").value("62701"));
    }

    @Test
    public void testGetAddressById_NotFound() throws Exception {
        Mockito.when(addressService.getAddressById(1L)).thenThrow(new CustomException("Address not found", HttpStatus.NOT_FOUND.value()));

        mockMvc.perform(get("/address/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAddressesByUserId_Success() throws Exception {
        AddressResponseDTO responseDTO1 = new AddressResponseDTO();
        responseDTO1.setAddressId(1L);
        responseDTO1.setUserId(1L);
        responseDTO1.setStreet("123 Main St");
        responseDTO1.setCity("Springfield");
        responseDTO1.setState("IL");
        responseDTO1.setPostalCode("62701");

        AddressResponseDTO responseDTO2 = new AddressResponseDTO();
        responseDTO2.setAddressId(2L);
        responseDTO2.setUserId(1L);
        responseDTO2.setStreet("456 Elm St");
        responseDTO2.setCity("Springfield");
        responseDTO2.setState("IL");
        responseDTO2.setPostalCode("62702");

        List<AddressResponseDTO> addresses = Arrays.asList(responseDTO1, responseDTO2);

        Mockito.when(addressService.getAddressesByUserId(1L)).thenReturn(addresses);

        mockMvc.perform(get("/address/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].addressId").value(1L))
                .andExpect(jsonPath("$[0].userId").value(1L))
                .andExpect(jsonPath("$[0].street").value("123 Main St"))
                .andExpect(jsonPath("$[1].addressId").value(2L))
                .andExpect(jsonPath("$[1].userId").value(1L))
                .andExpect(jsonPath("$[1].street").value("456 Elm St"));
    }

    @Test
    public void testUpdateAddress_Success() throws Exception {
        AddressCreateDTO addressCreateDTO = new AddressCreateDTO();
        addressCreateDTO.setUserId(1L);
        addressCreateDTO.setStreet("789 Oak St");
        addressCreateDTO.setCity("Springfield");
        addressCreateDTO.setState("IL");
        addressCreateDTO.setPostalCode("62703");

        AddressResponseDTO responseDTO = new AddressResponseDTO();
        responseDTO.setAddressId(1L);
        responseDTO.setUserId(1L);
        responseDTO.setStreet("789 Oak St");
        responseDTO.setCity("Springfield");
        responseDTO.setState("IL");
        responseDTO.setPostalCode("62703");

        Mockito.when(addressService.updateAddress(Mockito.eq(1L), Mockito.any(AddressCreateDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/address/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1,\"street\":\"789 Oak St\",\"city\":\"Springfield\",\"state\":\"IL\",\"postalCode\":\"62703\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.addressId").value(1L))
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.street").value("789 Oak St"))
                .andExpect(jsonPath("$.city").value("Springfield"))
                .andExpect(jsonPath("$.state").value("IL"))
                .andExpect(jsonPath("$.postalCode").value("62703"));
    }

    @Test
    public void testDeleteAddress_Success() throws Exception {
        Mockito.doNothing().when(addressService).deleteAddress(1L);

        mockMvc.perform(delete("/address/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteAddress_NotFound() throws Exception {
        Mockito.doThrow(new CustomException("Address not found", HttpStatus.NOT_FOUND.value())).when(addressService).deleteAddress(1L);

        mockMvc.perform(delete("/address/1"))
                .andExpect(status().isNotFound());
    }
}

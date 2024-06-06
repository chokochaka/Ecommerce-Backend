package com.spring.ecommerce.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.ecommerce.dto.AddressDto;
import com.spring.ecommerce.services.AddressService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = AddressController.class)
public class AddressControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService addressService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void addAddress_ShouldSaveAddress() throws Exception {
        // Given
        AddressDto addressDto = AddressDto.builder()
                .id(1L)
                .country("Country")
                .province("Province")
                .city("City")
                .address("123 Main St")
                .userId(1L)
                .build();
        doNothing().when(addressService).addAddress(any(AddressDto.class));

        // When
        ResultActions response = mockMvc.perform(post("/api/v1/address")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(addressDto)));

        // Then
        response.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateAddress_ShouldUpdateAddress() throws Exception {
        // Given
        AddressDto addressDto = AddressDto.builder()
                .id(1L)
                .country("Country")
                .province("Province")
                .city("City")
                .address("123 Main St")
                .userId(1L)
                .build();
        doNothing().when(addressService).updateAddress(any(AddressDto.class));

        // When
        ResultActions response = mockMvc.perform(put("/api/v1/address")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(addressDto)));

        // Then
        response.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getAddressByUserId_ShouldReturnAddress() throws Exception {
        // Given
        Long userId = 1L;
        AddressDto addressDto = AddressDto.builder()
                .id(1L)
                .country("Country")
                .province("Province")
                .city("City")
                .address("123 Main St")
                .userId(1L)
                .build();
        when(addressService.getAddressByUserId(anyLong())).thenReturn(addressDto);

        // When
        ResultActions response = mockMvc.perform(get("/api/v1/address/user/{userId}", userId)
                .contentType("application/json"));

        // Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(addressDto.getId()))
                .andExpect(jsonPath("$.country").value(addressDto.getCountry()))
                .andExpect(jsonPath("$.province").value(addressDto.getProvince()))
                .andExpect(jsonPath("$.city").value(addressDto.getCity()))
                .andExpect(jsonPath("$.address").value(addressDto.getAddress()))
                .andExpect(jsonPath("$.userId").value(addressDto.getUserId()));
    }
}

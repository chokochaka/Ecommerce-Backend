package com.spring.ecommerce.controllers;

import com.spring.ecommerce.dto.AddressDto;
import com.spring.ecommerce.services.AddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
@Tag(name = "Address", description = "Address API")
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public void addAddress(@Valid @RequestBody AddressDto addressDto) {
        addressService.addAddress(addressDto);
    }

    @PutMapping()
    public void updateAddress(@Valid @RequestBody AddressDto addressDto) {
        addressService.updateAddress(addressDto);
    }

    @GetMapping("/user/{userId}")
    public AddressDto getAddressByUserId(@PathVariable Long userId) {
        return addressService.getAddressByUserId(userId);
    }
}

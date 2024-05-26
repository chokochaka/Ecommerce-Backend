package com.spring.ecommerce.services;

import com.spring.ecommerce.dto.AddressDto;

public interface AddressService {
    void addAddress(AddressDto addressDto);

    void updateAddress(AddressDto addressDto);

    AddressDto getAddressByUserId(Long id);
}

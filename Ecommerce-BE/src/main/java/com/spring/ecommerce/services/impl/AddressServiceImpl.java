package com.spring.ecommerce.services.impl;

import com.spring.ecommerce.dto.AddressDto;
import com.spring.ecommerce.models.Address;
import com.spring.ecommerce.models.User;
import com.spring.ecommerce.repositories.AddressRepository;
import com.spring.ecommerce.repositories.UserRepository;
import com.spring.ecommerce.services.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class
AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Override
    public void addAddress(AddressDto addressDto) {
        User user = userRepository.findById(addressDto.getUserId()).orElseThrow();
        Address address = Address.builder()
                .country(addressDto.getCountry())
                .province(addressDto.getProvince())
                .city(addressDto.getCity())
                .address(addressDto.getAddress())
                .user(user)
                .build();
        addressRepository.save(address);
    }

    @Override
    public void updateAddress(AddressDto addressDto) {
        Address address = addressRepository.findById(addressDto.getId()).orElseThrow();
        address.setCountry(addressDto.getCountry());
        address.setProvince(addressDto.getProvince());
        address.setCity(addressDto.getCity());
        address.setAddress(addressDto.getAddress());
        addressRepository.save(address);
    }

    @Override
    public AddressDto getAddressByUserId(Long id) {
        Address address = addressRepository.findByUser_Id(id);
        return AddressDto.builder()
                .id(address.getId())
                .country(address.getCountry())
                .province(address.getProvince())
                .city(address.getCity())
                .address(address.getAddress())
                .userId(address.getUser().getId())
                .build();
    }
}

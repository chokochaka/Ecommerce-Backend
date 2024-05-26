package com.spring.ecommerce.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddressDto {
    private Long id;
    private String country;
    private String province;
    private String city;
    private String address;
    private Long userId;
}

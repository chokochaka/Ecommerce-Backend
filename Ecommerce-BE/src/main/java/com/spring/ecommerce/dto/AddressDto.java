package com.spring.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddressDto {

    private Long id;

    @NotBlank(message = "Country should not be blank")
    private String country;

    @NotBlank(message = "Province should not be blank")
    private String province;

    @NotBlank(message = "City should not be blank")
    private String city;

    @NotBlank(message = "Address should not be blank")
    private String address;

    @NotNull(message = "User ID should not be null")
    private Long userId;
}

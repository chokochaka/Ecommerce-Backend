package com.spring.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class VariationDto {

    private Long id;

    @NotBlank(message = "Name should not be blank")
    private String name;

    @NotNull(message = "Created on should not be null")
    private Instant createdOn;

    @NotNull(message = "Last updated on should not be null")
    private Instant lastUpdatedOn;

    @NotBlank(message = "Variation name should not be blank")
    private String variationName;
}

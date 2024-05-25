package com.spring.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class VariationDto {
    private Long id;
    private String name;
    private Instant createdOn;
    private Instant lastUpdatedOn;
}

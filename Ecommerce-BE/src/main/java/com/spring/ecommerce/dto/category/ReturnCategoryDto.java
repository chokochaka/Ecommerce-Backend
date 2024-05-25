package com.spring.ecommerce.dto.category;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ReturnCategoryDto {
    private Long id;
    private String name;
    private String description;
    private String parentCategoryName;
    private Instant createdOn;
    private Instant lastUpdatedOn;
}
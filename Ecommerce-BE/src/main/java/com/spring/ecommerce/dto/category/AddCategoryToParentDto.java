package com.spring.ecommerce.dto.category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCategoryToParentDto {
    private long parentCategoryId;
    private String name;
    private String description;
}

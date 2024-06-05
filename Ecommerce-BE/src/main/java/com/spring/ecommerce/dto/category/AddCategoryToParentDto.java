package com.spring.ecommerce.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCategoryToParentDto {

    @NotNull(message = "Parent category ID should not be null")
    private long parentCategoryId;

    @NotBlank(message = "Name should not be blank")
    private String name;

    @NotBlank(message = "Description should not be blank")
    private String description;
}

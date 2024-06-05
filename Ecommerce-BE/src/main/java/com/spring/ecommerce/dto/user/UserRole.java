package com.spring.ecommerce.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRole {

    @NotBlank(message = "Role name should not be blank")
    private String roleName;
}

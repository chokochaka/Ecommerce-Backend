package com.spring.ecommerce.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UpdateUserDto {
    private String email;
    private String firstName;
    private String lastName;
    private boolean enabled;
    Set<Long> roleIds;
}

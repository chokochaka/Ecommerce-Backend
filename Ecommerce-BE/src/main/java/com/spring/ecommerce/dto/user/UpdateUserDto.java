package com.spring.ecommerce.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;

import java.util.Set;

@Getter
@Setter
@Builder
public class UpdateUserDto {
    private String email;
    private String firstName;
    private String lastName;
    private boolean enabled;
    private String password;
    Set<String> roleNames;
}

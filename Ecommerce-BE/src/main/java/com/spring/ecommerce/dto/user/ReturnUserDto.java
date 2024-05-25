package com.spring.ecommerce.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
public class ReturnUserDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private boolean enabled;
    private Set<UserRole> roles;
    private UserRefreshToken refreshToken;

    private Instant createdOn;
    private Instant lastUpdatedOn;
}


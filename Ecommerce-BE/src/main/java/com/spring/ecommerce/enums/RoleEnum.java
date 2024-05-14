package com.spring.ecommerce.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleEnum {
    ROLE_USER("USER"),
    ROLE_INVENTORY_MANAGER("INVENTORY_MANAGER"),
    ROLE_ADMIN("ADMIN");

    private final String roleName;

}
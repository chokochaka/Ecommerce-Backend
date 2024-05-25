package com.spring.ecommerce.services;

import com.spring.ecommerce.dto.user.UserRole;
import com.spring.ecommerce.models.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();
}

package com.spring.ecommerce.services.impl;

import com.spring.ecommerce.models.Role;
import com.spring.ecommerce.repositories.RoleRepository;
import com.spring.ecommerce.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}

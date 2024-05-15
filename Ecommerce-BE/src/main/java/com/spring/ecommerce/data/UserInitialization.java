package com.spring.ecommerce.data;

import com.spring.ecommerce.enums.RoleEnum;
import com.spring.ecommerce.models.Role;
import com.spring.ecommerce.models.User;
import com.spring.ecommerce.repositories.RoleRepository;
import com.spring.ecommerce.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserInitialization {

    private static final String USER_ROLE = RoleEnum.ROLE_USER.getRoleName();
    private static final String INVENTORY_MANAGER_ROLE = RoleEnum.ROLE_INVENTORY_MANAGER.getRoleName();
    private static final String ADMIN_ROLE = RoleEnum.ROLE_ADMIN.getRoleName();
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initData() {
        createRoles();
        createUsers();
    }

    private void createRoles() {
        List<Role> roles = List.of(
                Role.builder().roleName(USER_ROLE).build(),
                Role.builder().roleName(INVENTORY_MANAGER_ROLE).build(),
                Role.builder().roleName(ADMIN_ROLE).build()
        );
        roleRepository.saveAll(roles);
    }

    private void createUsers() {
        List<User> users = List.of(
                createUser("test1@gmail.com", List.of(USER_ROLE, INVENTORY_MANAGER_ROLE, ADMIN_ROLE)),
                createUser("test2@gmail.com", List.of(USER_ROLE, INVENTORY_MANAGER_ROLE)),
                createUser("test3@gmail.com", List.of(USER_ROLE))
        );
        userRepository.saveAll(users);
    }

    private User createUser(String email, List<String> roles) {
        Set<Role> userRoles = roles.stream()
                .map(roleName -> roleRepository.findByRoleName(roleName).orElseThrow())
                .collect(Collectors.toSet());
        return User.builder()
                .email(email)
                .password(passwordEncoder.encode("123"))
                .roles(userRoles)
                .build();
    }
}
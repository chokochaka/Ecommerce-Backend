package com.spring.ecommerce.controllers;

import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.dto.user.ReturnUserDto;
import com.spring.ecommerce.dto.user.UpdateUserDto;
import com.spring.ecommerce.models.User;
import com.spring.ecommerce.repositories.UserRepository;
import com.spring.ecommerce.services.FilterSpecificationService;
import com.spring.ecommerce.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "User API")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    private final FilterSpecificationService<User> userFilterSpecificationService;

    @PostMapping("/search")
    public List<ReturnUserDto> getUsersBySearch(
            @RequestBody SearchRequestDto searchRequestDto
    ) {
        return userService.getUsersBySearch(searchRequestDto);
    }

    @PostMapping("/search/paginated")
    public Page<ReturnUserDto> getUsersBySearchAndPagination(
            @RequestBody SearchRequestDto searchRequestDto
    ) {
        return userService.getUsersBySearchAndPagination(searchRequestDto);
    }

    @PostMapping
    public void createUser(
            @RequestBody UpdateUserDto updateUserDto
    ) {
        userService.createUser(updateUserDto);
    }

    @PutMapping("/{id}")
    public void updateUser(
            @PathVariable long id,
            @RequestBody UpdateUserDto updateUserDto
    ) {
        userService.updateUser(id, updateUserDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
    }
}

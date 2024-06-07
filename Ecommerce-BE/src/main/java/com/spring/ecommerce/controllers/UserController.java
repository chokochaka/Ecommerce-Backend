package com.spring.ecommerce.controllers;

import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.dto.user.ReturnUserDto;
import com.spring.ecommerce.dto.user.UpdateUserDto;
import com.spring.ecommerce.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final UserService userService;

    @PostMapping("/search")
    public ResponseEntity<List<ReturnUserDto>> getUsersBySearch(
            @Valid @RequestBody SearchRequestDto searchRequestDto
    ) {
        List<ReturnUserDto> users = userService.getUsersBySearch(searchRequestDto);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/search/paginated")
    public ResponseEntity<Page<ReturnUserDto>> getUsersBySearchAndPagination(
            @Valid @RequestBody SearchRequestDto searchRequestDto
    ) {
        Page<ReturnUserDto> users = userService.getUsersBySearchAndPagination(searchRequestDto);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(
            @Valid @RequestBody UpdateUserDto updateUserDto
    ) {
        userService.createUser(updateUserDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(
            @PathVariable long id,
            @Valid @RequestBody UpdateUserDto updateUserDto
    ) {
        userService.updateUser(id, updateUserDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

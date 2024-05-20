package com.spring.ecommerce.controllers;

import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.models.User;
import com.spring.ecommerce.repositories.UserRepository;
import com.spring.ecommerce.services.FilterSpecificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final FilterSpecificationService<User> userFilterSpecificationService;

    @PostMapping("/search")
    public List<User> getProductsBySpecification(
            @RequestBody SearchRequestDto searchRequestDto
    ) {
        Specification<User> userSearchSpecification = userFilterSpecificationService
                .getSearchSpecification(
                        searchRequestDto.getFieldRequestDtos()
                        , searchRequestDto.getGlobalOperator()
                );
        return userRepository.findAll(userSearchSpecification);
    }
}
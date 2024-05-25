package com.spring.ecommerce.services.impl;

import com.spring.ecommerce.dto.search.PageRequestDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.dto.user.ReturnUserDto;
import com.spring.ecommerce.dto.user.UpdateUserDto;
import com.spring.ecommerce.exceptions.ResourceNotFoundException;
import com.spring.ecommerce.mapper.UserMapper;
import com.spring.ecommerce.models.Role;
import com.spring.ecommerce.models.User;
import com.spring.ecommerce.repositories.RoleRepository;
import com.spring.ecommerce.repositories.UserRepository;
import com.spring.ecommerce.services.FilterSpecificationService;
import com.spring.ecommerce.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final FilterSpecificationService<User> userFilterSpecificationService;
    private final RoleRepository roleRepository;

    @Override
    public List<ReturnUserDto> getUsersBySearch(SearchRequestDto searchRequestDto) {
        Specification<User> userSearchSpecification = userFilterSpecificationService
                .getSearchSpecification(
                        searchRequestDto.getFieldRequestDtos()
                        , searchRequestDto.getGlobalOperator()
                );
        List<User> products = userRepository.findAll(userSearchSpecification);
        return userRepository.findAll(userSearchSpecification).stream()
                .map(userMapper::userToReturnUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ReturnUserDto> getUsersBySearchAndPagination(SearchRequestDto searchRequestDto) {
        Specification<User> userSearchSpecification = userFilterSpecificationService
                .getSearchSpecification(
                        searchRequestDto.getFieldRequestDtos()
                        , searchRequestDto.getGlobalOperator()
                );
        Pageable pageable = new PageRequestDto().getPageable(searchRequestDto.getPageRequestDto());
        Page<User> productPage = userRepository.findAll(userSearchSpecification, pageable);
        return productPage.map(userMapper::userToReturnUserDto);
    }

    @Override
    public void updateUser(long id, UpdateUserDto updateUserDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        user.setFirstName(updateUserDto.getFirstName());
        user.setLastName(updateUserDto.getLastName());
        user.setEnabled(updateUserDto.isEnabled());
        // roles
        Set<String> roleNames = updateUserDto.getRoleNames();
        List<Role> roleList = roleRepository.findAllById(roleNames);
        Set<Role> roles = new HashSet<>(roleList);
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}

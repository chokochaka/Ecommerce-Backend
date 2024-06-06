package com.spring.ecommerce.services;

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
import com.spring.ecommerce.services.impl.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private FilterSpecificationService<User> userFilterSpecificationService;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private ReturnUserDto returnUserDto;
    private SearchRequestDto searchRequestDto;
    private UpdateUserDto updateUserDto;
    private Role role;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setId(1L);

        returnUserDto = ReturnUserDto.builder()
                .id(1L)
                .build();

        searchRequestDto = new SearchRequestDto();
        searchRequestDto.setPageRequestDto(new PageRequestDto());

        updateUserDto = UpdateUserDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password")
                .enabled(true)
                .roleNames(Set.of("ROLE_USER"))
                .build();

        role = new Role();
        role.setRoleName("ROLE_USER");
    }

    @AfterEach
    public void tearDown() {
        reset(userRepository, userMapper, userFilterSpecificationService, roleRepository, passwordEncoder);
    }

    @DisplayName("JUnit test for getUsersBySearch method")
    @Test
    public void getUsersBySearch_ShouldReturnUsers() {
        // Given
        Specification<User> specification = mock(Specification.class);
        when(userFilterSpecificationService.getSearchSpecification(
                searchRequestDto.getFieldRequestDtos(), searchRequestDto.getGlobalOperator()
        )).thenReturn(specification);

        when(userRepository.findAll(specification)).thenReturn(Collections.singletonList(user));
        when(userMapper.userToReturnUserDto(user)).thenReturn(returnUserDto);

        // When
        List<ReturnUserDto> result = userService.getUsersBySearch(searchRequestDto);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(returnUserDto.getId());
    }

    @DisplayName("JUnit test for updateUser method")
    @Test
    public void updateUser_ShouldUpdateUser() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roleRepository.findAllById(updateUserDto.getRoleNames())).thenReturn(Collections.singletonList(role));

        // When
        userService.updateUser(1L, updateUserDto);

        // Then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(userArgumentCaptor.capture());
        User updatedUser = userArgumentCaptor.getValue();

        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getFirstName()).isEqualTo(updateUserDto.getFirstName());
        assertThat(updatedUser.getRoles()).hasSize(1);
    }

    @DisplayName("JUnit test for deleteUser method")
    @Test
    public void deleteUser_ShouldDeleteUser() {
        // Given
        long userId = 1L;

        // When
        userService.deleteUser(userId);

        // Then
        verify(userRepository, times(1)).deleteById(userId);
    }
}

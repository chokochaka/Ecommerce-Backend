package com.spring.ecommerce.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.dto.user.ReturnUserDto;
import com.spring.ecommerce.dto.user.UpdateUserDto;
import com.spring.ecommerce.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = UserController.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getUsersBySearch_ShouldReturnListOfUsers() throws Exception {
        // Given
        SearchRequestDto searchRequestDto = new SearchRequestDto();
        ReturnUserDto returnUserDto = ReturnUserDto.builder()
                .id(1L)
                .email("ttt@gmail.com")
                .firstName("John")
                .lastName("Doe")
                .build();
        List<ReturnUserDto> returnUserDtoList = Collections.singletonList(returnUserDto);
        when(userService.getUsersBySearch(any(SearchRequestDto.class))).thenReturn(returnUserDtoList);

        // When
        ResultActions response = mockMvc.perform(post("/api/v1/user/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(searchRequestDto)));

        // Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(returnUserDtoList.size())))
                .andExpect(jsonPath("$[0].id", is(returnUserDto.getId().intValue())));
    }

    @Test
    public void getUsersBySearchAndPagination_ShouldReturnPagedUsers() throws Exception {
        // Given
        SearchRequestDto searchRequestDto = new SearchRequestDto();
        ReturnUserDto returnUserDto = ReturnUserDto.builder()
                .id(1L)
                .email("ttt@gmail.com")
                .firstName("John")
                .lastName("Doe")
                .build();
        Page<ReturnUserDto> returnUserDtoPage = new PageImpl<>(Collections.singletonList(returnUserDto));
        when(userService.getUsersBySearchAndPagination(any(SearchRequestDto.class))).thenReturn(returnUserDtoPage);

        // When
        ResultActions response = mockMvc.perform(post("/api/v1/user/search/paginated")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(searchRequestDto)));

        // Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", is((int) returnUserDtoPage.getTotalElements())))
                .andExpect(jsonPath("$.content[0].id", is(returnUserDto.getId().intValue())));
    }

    @Test
    public void createUser_ShouldCreateUser() throws Exception {
        // Given
        UpdateUserDto updateUserDto = UpdateUserDto.builder()
                .email("ttt1@gmail.com")
                .firstName("John")
                .lastName("Doe")
                .password("password")
                .build();
        doNothing().when(userService).createUser(any(UpdateUserDto.class));

        // When
        ResultActions response = mockMvc.perform(post("/api/v1/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateUserDto)));

        // Then
        response.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateUser_ShouldUpdateExistingUser() throws Exception {
        // Given
        long userId = 1L;
        UpdateUserDto updateUserDto = UpdateUserDto.builder()
                .email("ttt1@gmail.com")
                .firstName("John")
                .lastName("Doe")
                .password("password")
                .build();
        doNothing().when(userService).updateUser(anyLong(), any(UpdateUserDto.class));

        // When
        ResultActions response = mockMvc.perform(put("/api/v1/user/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateUserDto)));

        // Then
        response.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUser_ShouldDeleteUser() throws Exception {
        // Given
        long userId = 1L;
        doNothing().when(userService).deleteUser(userId);

        // When
        ResultActions response = mockMvc.perform(delete("/api/v1/user/{id}", userId));

        // Then
        response.andDo(print())
                .andExpect(status().isOk());
    }
}

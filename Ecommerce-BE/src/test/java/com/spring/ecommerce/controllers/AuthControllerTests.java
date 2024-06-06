package com.spring.ecommerce.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.ecommerce.dto.auth.ActiveAccountDto;
import com.spring.ecommerce.dto.auth.RefreshTokenDto;
import com.spring.ecommerce.dto.auth.SignInDto;
import com.spring.ecommerce.dto.auth.TokenDto;
import com.spring.ecommerce.services.AuthService;
import com.spring.ecommerce.services.MailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = AuthController.class)
public class AuthControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private MailService mailService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void login_ShouldAuthenticateAndGenerateTokens() throws Exception {
        // Given
        SignInDto signInDto = SignInDto.builder()
                .email("test@example.com")
                .password("password")
                .build();
        TokenDto tokenDto = TokenDto.builder()
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .build();
        when(authService.login(any(SignInDto.class))).thenReturn(tokenDto);

        // When
        ResultActions response = mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signInDto)));

        // Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken", is(tokenDto.getAccessToken())))
                .andExpect(jsonPath("$.refreshToken", is(tokenDto.getRefreshToken())));
    }

    @Test
    public void logout_ShouldLogoutUser() throws Exception {
        // Given
        String email = "test@example.com";
        when(authService.logout(anyString())).thenReturn("Logout successfully");

        // When
        ResultActions response = mockMvc.perform(post("/api/v1/auth/logout/{email}", email)
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Logout successfully")));
    }

    @Test
    public void active_ShouldActivateAccount() throws Exception {
        // Given
        ActiveAccountDto activeAccountDto = ActiveAccountDto.builder()
                .email("test@example.com")
                .verificationCode("verificationCode")
                .build();
        when(authService.active(anyString(), anyString())).thenReturn("Account activated successfully");

        // When
        ResultActions response = mockMvc.perform(post("/api/v1/auth/active")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(activeAccountDto)));

        // Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Account activated successfully")));
    }

    @Test
    public void refreshToken_ShouldGenerateNewTokens() throws Exception {
        // Given
        RefreshTokenDto refreshTokenDto = RefreshTokenDto.builder()
                .refreshToken("refreshToken")
                .build();
        TokenDto tokenDto = TokenDto.builder()
                .accessToken("accessToken")
                .refreshToken("newRefreshToken")
                .build();
        when(authService.refreshToken(any(RefreshTokenDto.class))).thenReturn(tokenDto);

        // When
        ResultActions response = mockMvc.perform(post("/api/v1/auth/refresh-token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(refreshTokenDto)));

        // Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken", is(tokenDto.getAccessToken())))
                .andExpect(jsonPath("$.refreshToken", is(tokenDto.getRefreshToken())));
    }
}

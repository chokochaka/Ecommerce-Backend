package com.spring.ecommerce.controllers;

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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = MailController.class)
public class MailControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MailService mailService;

    @Test
    public void sendOtpForgotPassword_ShouldSendOtpAndReturnSuccessMessage() throws Exception {
        // Given
        String recipientEmail = "test@example.com";
        String successMessage = "OTP sent successfully";
        when(mailService.sendOtpForgotPassword(anyString())).thenReturn(successMessage);

        // When
        ResultActions response = mockMvc.perform(post("/api/v1/mail/send-forgot-password/{recipientEmail}", recipientEmail)
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(successMessage)));
    }

    @Test
    public void sendVerifyAccount_ShouldSendVerificationAndReturnSuccessMessage() throws Exception {
        // Given
        String recipientEmail = "test@example.com";
        String successMessage = "Verification email sent successfully";
        when(mailService.sendVerifyAccount(anyString())).thenReturn(successMessage);

        // When
        ResultActions response = mockMvc.perform(post("/api/v1/mail/send-verify-account/{recipientEmail}", recipientEmail)
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(successMessage)));
    }
}

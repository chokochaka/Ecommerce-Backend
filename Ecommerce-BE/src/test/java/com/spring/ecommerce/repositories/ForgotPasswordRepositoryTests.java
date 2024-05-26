package com.spring.ecommerce.repositories;

import com.spring.ecommerce.models.ForgotPassword;
import com.spring.ecommerce.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ForgotPasswordRepositoryTests {

    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private ForgotPassword forgotPassword;

    @BeforeEach
    void setUp() {
        // Setup user
        user = User.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane.doe@example.com")
                .enabled(true)
                .build();
        user = userRepository.save(user);

        // Setup forgot password
        forgotPassword = ForgotPassword.builder()
                .otp(123456)
                .user(user)
                .issuedAt(Instant.ofEpochSecond(System.currentTimeMillis()))
                .expiresAt(Instant.ofEpochSecond(System.currentTimeMillis() + 300000))
                .build();
        forgotPassword = forgotPasswordRepository.save(forgotPassword);
    }

    @DisplayName("JUnit test for findByOtpAndUser when OTP and User exist")
    @Test
    public void findByOtpAndUser_WhenOtpAndUserExist_ReturnsForgotPassword() {
        // When
        Optional<ForgotPassword> foundForgotPassword = forgotPasswordRepository.findByOtpAndUser(123456, user);

        // Then
        assertThat(foundForgotPassword).isPresent();
        assertThat(foundForgotPassword.get().getOtp()).isEqualTo(123456);
        assertThat(foundForgotPassword.get().getUser().getId()).isEqualTo(user.getId());
    }

    @DisplayName("JUnit test for findByOtpAndUser when OTP and User do not exist")
    @Test
    public void findByOtpAndUser_WhenOtpAndUserDoNotExist_ReturnsEmpty() {
        // When
        Optional<ForgotPassword> foundForgotPassword = forgotPasswordRepository.findByOtpAndUser(999999, user);

        // Then
        assertThat(foundForgotPassword).isNotPresent();
    }

    @DisplayName("JUnit test for findByOtpAndUser when User does not match")
    @Test
    public void findByOtpAndUser_WhenUserDoesNotMatch_ReturnsEmpty() {
        // Given
        User anotherUser = User.builder()
                .firstName("John")
                .lastName("Smith")
                .email("john.smith@example.com")
                .build();
        anotherUser = userRepository.save(anotherUser);

        // When
        Optional<ForgotPassword> foundForgotPassword = forgotPasswordRepository.findByOtpAndUser(123456, anotherUser);

        // Then
        assertThat(foundForgotPassword).isNotPresent();
    }
}
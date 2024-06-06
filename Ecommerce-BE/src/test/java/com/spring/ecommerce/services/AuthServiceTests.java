package com.spring.ecommerce.services;

import com.spring.ecommerce.config.Constant;
import com.spring.ecommerce.dto.auth.PasswordDto;
import com.spring.ecommerce.dto.auth.RefreshTokenDto;
import com.spring.ecommerce.dto.auth.SignInDto;
import com.spring.ecommerce.dto.auth.SignUpDto;
import com.spring.ecommerce.dto.auth.TokenDto;
import com.spring.ecommerce.enums.RoleEnum;
import com.spring.ecommerce.models.RefreshToken;
import com.spring.ecommerce.models.Role;
import com.spring.ecommerce.models.User;
import com.spring.ecommerce.repositories.RefreshTokenRepository;
import com.spring.ecommerce.repositories.RoleRepository;
import com.spring.ecommerce.repositories.UserRepository;
import com.spring.ecommerce.services.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTService jwtService;

    @Mock
    private MailService mailService;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @InjectMocks
    private AuthServiceImpl authService;

    private User user;
    private Role role;
    private SignUpDto signUpDto;
    private SignInDto signInDto;
    private RefreshToken refreshToken;

    @BeforeEach
    public void setup() {
        role = new Role();
        role.setRoleName(RoleEnum.ROLE_USER.getRoleName());

        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setVerificationCode("verificationCode");
        user.setEnabled(false);
        user.setRoles(Set.of(role));

        signUpDto = SignUpDto.builder()
                .email("test@example.com")
                .password("password")
                .firstName("John")
                .lastName("Doe")
                .build();

        signInDto = SignInDto.builder()
                .email("test@example.com")
                .password("password")
                .build();

        refreshToken = new RefreshToken();
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setIssuedAt(Instant.now());
        refreshToken.setExpiresAt(Instant.now().plusMillis(Constant.TIME.FOURTEEN_DAYS));
        refreshToken.setUser(user);
    }

    @Test
    public void logout_ShouldDeleteRefreshToken() {
        // Given
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        doNothing().when(refreshTokenRepository).deleteByUser(any(User.class));

        // When
        String result = authService.logout("test@example.com");

        // Then
        assertThat(result).isEqualTo("Logout successfully");
    }

    @Test
    public void active_ShouldActivateAccount() {
        // Given
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        // When
        String result = authService.active("test@example.com", "verificationCode");

        // Then
        assertThat(result).isEqualTo("Account activated successfully");
    }

    @Test
    public void active_ShouldThrowException_WhenVerificationCodeIsInvalid() {
        // Given
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        // Then
        assertThrows(RuntimeException.class, () -> {
            authService.active("test@example.com", "invalidCode");
        });
    }

    @Test
    public void refreshToken_ShouldGenerateNewTokens() {
        // Given
        RefreshTokenDto refreshTokenDto = RefreshTokenDto.builder()
                .refreshToken(refreshToken.getRefreshToken())
                .build();
        when(refreshTokenRepository.findByRefreshToken(anyString())).thenReturn(Optional.of(refreshToken));
        when(jwtService.generateToken(any(User.class))).thenReturn("accessToken");

        // When
        TokenDto tokenDto = authService.refreshToken(refreshTokenDto);

        // Then
        assertThat(tokenDto).isNotNull();
        assertThat(tokenDto.getAccessToken()).isEqualTo("accessToken");
        assertThat(tokenDto.getRefreshToken()).isEqualTo(refreshToken.getRefreshToken());
    }

    @Test
    public void changePassword_ShouldThrowException_WhenPasswordsDoNotMatch() {
        // Given
        PasswordDto passwordDto = new PasswordDto() {
            @Override
            public String password() {
                return "newPassword";
            }

            @Override
            public String repeatPassword() {
                return "newPassword1";
            }
        };

        // Then
        assertThrows(RuntimeException.class, () -> {
            authService.changePassword(passwordDto, "test@example.com", "Bearer token", false);
        });
    }

    @Test
    public void changePassword_ShouldThrowException_WhenAuthenticationFails() {
        // Given
        PasswordDto passwordDto = new PasswordDto() {
            @Override
            public String password() {
                return "newPassword";
            }

            @Override
            public String repeatPassword() {
                return "newPassword";
            }
        };
        when(jwtService.extractUsername(anyString())).thenReturn("another@example.com");

        // Then
        assertThrows(RuntimeException.class, () -> {
            authService.changePassword(passwordDto, "test@example.com", "Bearer token", false);
        });
    }
}

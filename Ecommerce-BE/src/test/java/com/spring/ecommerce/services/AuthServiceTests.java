//package com.spring.ecommerce.services;
//
//import com.spring.ecommerce.dto.auth.RefreshTokenDto;
//import com.spring.ecommerce.dto.auth.SignInDto;
//import com.spring.ecommerce.dto.auth.SignUpDto;
//import com.spring.ecommerce.dto.auth.TokenDto;
//import com.spring.ecommerce.enums.RoleEnum;
//import com.spring.ecommerce.models.RefreshToken;
//import com.spring.ecommerce.models.Role;
//import com.spring.ecommerce.models.User;
//import com.spring.ecommerce.repositories.RoleRepository;
//import com.spring.ecommerce.repositories.UserRepository;
//import com.spring.ecommerce.services.impl.AuthServiceImpl;
//import com.spring.ecommerce.services.impl.JwtServiceImpl;
//import com.spring.ecommerce.services.impl.RefreshTokenServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.mockito.junit.jupiter.MockitoSettings;
//import org.mockito.quality.Strictness;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.Optional;
//import java.util.Set;
//
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//
//@ExtendWith(MockitoExtension.class)
//public class AuthServiceTests {
//
//    private final String accessToken = "accessToken";
//    private final RefreshToken refreshToken = RefreshToken
//            .builder().refreshToken("refreshToken").build();
//
//    @Mock
//    private UserRepository userRepository;
//    @Mock
//    private RoleRepository roleRepository;
//    @Mock
//    private PasswordEncoder passwordEncoder;
//    @Mock
//    private AuthenticationManager authenticationManager;
//    @Mock
//    private JwtServiceImpl jwtServiceImpl;
//    @Mock
//    private RefreshTokenServiceImpl refreshTokenServiceImpl;
//    @InjectMocks
//    private AuthServiceImpl authService;
//    private SignUpDto signUpDto;
//    private SignInDto signInDto;
//
//    private User savedUser;
//    private Role userRole;
//
//    @BeforeEach
//    public void setup() {
//        userRole = new Role(RoleEnum.ROLE_USER.getRoleName(), null);
//        savedUser = User.builder()
//                .email("test@example.com")
//                .password("password")
//                .roles(Set.of(userRole))
//                .build();
//
//        signUpDto = new SignUpDto("test@example.com", "password");
//        signInDto = new SignInDto("test@example.com", "password");
//    }
//
//    @DisplayName("Test Register Method")
//    @Test
//    public void givenSignUpDto_whenRegister_thenReturnTokenDto() {
//
//        // given
//        given(roleRepository.findByRoleName(RoleEnum.ROLE_USER.getRoleName()))
//                .willReturn(Optional.of(userRole));
//        given(userRepository.save(any(User.class)))
//                .willReturn(savedUser);
//        given(jwtServiceImpl.generateToken(savedUser))
//                .willReturn(accessToken);
//        given(refreshTokenServiceImpl.generateRefreshToken(savedUser.getEmail()))
//                .willReturn(refreshToken);
//
//        // when
//        TokenDto tokenDto = authService.register(signUpDto);
//
//        // then
//        assertThat(tokenDto).isNotNull();
//        assertThat(tokenDto.getAccessToken()).isNotNull().isEqualTo(accessToken);
//        assertThat(tokenDto.getRefreshToken()).isNotNull().isEqualTo(refreshToken.getRefreshToken());
//
//    }
//
//    @DisplayName("Test Login Method")
//    @Test
//    public void givenSignIpDto_whenLogin_thenReturnTokenDto() {
//
//        // given
//        given(userRepository.findByEmail(signInDto.getEmail()))
//                .willReturn(Optional.ofNullable(savedUser));
//        given(jwtServiceImpl.generateToken(savedUser))
//                .willReturn(accessToken);
//        given(refreshTokenServiceImpl.generateRefreshToken(savedUser.getEmail()))
//                .willReturn(refreshToken);
//
//        // when
//        TokenDto tokenDto = authService.login(signInDto);
//
//        // then
//        assertThat(tokenDto).isNotNull();
//        assertThat(tokenDto.getAccessToken()).isNotNull().isEqualTo(accessToken);
//        assertThat(tokenDto.getRefreshToken()).isNotNull().isEqualTo(refreshToken.getRefreshToken());
//
//    }
//
////    @DisplayName("Test login method")
////    @Test
////    public void testLogin() {
////        Role userRole = new Role(RoleEnum.ROLE_USER);
////        User user = new User("test@example.com", "encodedPassword", Set.of(userRole));
////
////        when(userRepository.findByEmail(signInDto.getEmail())).thenReturn(Optional.of(user));
////        when(jwtServiceImpl.generateToken(any(User.class))).thenReturn("accessToken");
////
////        TokenDto tokenDto = authService.login(signInDto);
////
////        assertThat(tokenDto).isNotNull();
////        assertThat(tokenDto.getAccessToken()).isNotNull();
////        assertThat(tokenDto.getRefreshToken()).isNotNull();
////    }
////
////    @DisplayName("Test refreshToken method")
////    @Test
////    public void testRefreshToken() {
////        Role userRole = new Role(RoleEnum.ROLE_USER);
////        User user = new User("test@example.com", "encodedPassword", Set.of(userRole));
////        RefreshToken refreshToken = new RefreshToken("refreshToken", user);
////
////        when(refreshTokenServiceImpl.verifyRefreshToken(refreshTokenDto.getRefreshToken())).thenReturn(refreshToken);
////        when(jwtServiceImpl.generateToken(any(User.class))).thenReturn("accessToken");
////
////        TokenDto tokenDto = authService.refreshToken(refreshTokenDto);
////
////        assertThat(tokenDto).isNotNull();
////        assertThat(tokenDto.getAccessToken()).isNotNull();
////        assertThat(tokenDto.getRefreshToken()).isNotNull();
////    }
//
////    @DisplayName("Test changePassword method")
////    @Test
////    public void testChangePassword() {
////        String newPassword = "newPassword";
////        String email = "test@example.com";
////
////        // Mock repository updatePassword method
////
////        String result = authService.changePassword(newPassword, email);
////
////        assertThat(result).isEqualTo("Password changed successfully");
////    }
//}

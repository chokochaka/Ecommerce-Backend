package com.spring.ecommerce.security;

import com.spring.ecommerce.security.filter.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    @Value("${security.api.admin}")
    private String adminApi;
    @Value("${security.api.shop}")
    private String shopApi;

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList(adminApi, shopApi));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowCredentials(true);
        config.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        config.setExposedHeaders(List.of("Authorization"));
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
//        requestHandler.setCsrfRequestAttributeName("_csrf");
        return http
                .cors(corsCustomizer -> corsCustomizer.configurationSource(corsConfigurationSource()))
//                .csrf(
//                        (csrf) -> csrf.csrfTokenRequestHandler(requestHandler)
//                                .ignoringRequestMatchers("/contact", "/register")
//                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                        )
//                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
//                .addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
//                .addFilterAt(new AuthoritiesLoggingAtFilter(),BasicAuthenticationFilter.class)
//                .addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authorizeHttpRequests(req ->
                        req
                                // for testing only
//                                .requestMatchers("/api/v1/admin/user")
//                                .hasRole(RoleEnum.ROLE_USER.getRoleName())
//                                .requestMatchers("/api/v1/admin/iv")
//                                .hasRole(RoleEnum.ROLE_INVENTORY_MANAGER.getRoleName())
//                                .requestMatchers("/api/v1/admin/admin")
//                                .hasRole(RoleEnum.ROLE_ADMIN.getRoleName())
//                                .requestMatchers("/api/v1/admin/all")
//                                .permitAll()
//
//                                .requestMatchers("/api/v1/auth/change-password/*").authenticated()
                                // swagger - open api
                                .requestMatchers(
                                        "/swagger-ui/**",
                                        "/v2/api-docs/**", "/v3/api-docs/**",
                                        "/swagger-ui.html", "/swagger-resources/**", "/webjars/**"
                                ).permitAll()

//                                .requestMatchers("/api/v1/product/**").hasAnyRole(RoleEnum.ROLE_ADMIN.getRoleName())
//                                .requestMatchers("/api/v1/product/*").hasAnyRole(RoleEnum.ROLE_ADMIN.getRoleName())

                                .anyRequest().permitAll())
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}

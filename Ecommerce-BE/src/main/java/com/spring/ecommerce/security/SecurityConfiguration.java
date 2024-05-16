package com.spring.ecommerce.security;

import com.spring.ecommerce.enums.RoleEnum;
import com.spring.ecommerce.security.filter.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authorizeHttpRequests(req ->
                        req
                                // for testing only
                                .requestMatchers("/api/v1/admin/user")
                                .hasRole(RoleEnum.ROLE_USER.getRoleName())
                                .requestMatchers("/api/v1/admin/iv")
                                .hasRole(RoleEnum.ROLE_INVENTORY_MANAGER.getRoleName())
                                .requestMatchers("/api/v1/admin/admin")
                                .hasRole(RoleEnum.ROLE_ADMIN.getRoleName())
                                .requestMatchers("/api/v1/admin/all")
                                .permitAll()

                                .requestMatchers("/api/v1/auth/change-password/*").authenticated()
                                .anyRequest().permitAll())
//                                .anyRequest().authenticated())
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}

package com.spring.ecommerce.security;

import com.spring.ecommerce.enums.RoleEnum;
import com.spring.ecommerce.security.filter.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

        String ADMIN = RoleEnum.ROLE_ADMIN.getRoleName();
        String INVENTORY_MANAGER = RoleEnum.ROLE_INVENTORY_MANAGER.getRoleName();
        String USER = RoleEnum.ROLE_USER.getRoleName();

        return http
                .cors(corsCustomizer -> corsCustomizer.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authorizeHttpRequests(req ->
                        req
                                // swagger - open api
                                .requestMatchers(
                                        "/swagger-ui/**",
                                        "/v2/api-docs/**", "/v3/api-docs/**",
                                        "/swagger-ui.html", "/swagger-resources/**", "/webjars/**"
                                ).permitAll()
                                // product item
                                .requestMatchers(HttpMethod.POST, "/api/v1/productItem").hasAnyRole(ADMIN, INVENTORY_MANAGER)
                                .requestMatchers(HttpMethod.PUT, "/api/v1/productItem/*").hasAnyRole(ADMIN, INVENTORY_MANAGER)
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/productItem/*").hasAnyRole(ADMIN, INVENTORY_MANAGER)
                                // address
                                .requestMatchers(HttpMethod.POST, "/api/v1/address").hasAnyRole(ADMIN, USER)
                                .requestMatchers(HttpMethod.PUT, "/api/v1/address").hasAnyRole(ADMIN, USER)
                                // category
                                .requestMatchers("/api/v1/category/search").permitAll()
                                .requestMatchers("/api/v1/category/search/paginated").permitAll()
                                .requestMatchers("/api/v1/category/parent/search").permitAll()
                                .requestMatchers("/api/v1/category/parent/search/paginated").permitAll()
                                .requestMatchers("/api/v1/category").hasAnyRole(ADMIN, INVENTORY_MANAGER)
                                .requestMatchers("/api/v1/category/**").hasAnyRole(ADMIN, INVENTORY_MANAGER)
                                // order
                                .requestMatchers("/api/v1/order/search").permitAll()
                                .requestMatchers("/api/v1/order/search/paginated").permitAll()
                                .requestMatchers("/api/v1/order/canUserComment").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/order").permitAll()
                                .requestMatchers("/api/v1/order/**").hasAnyRole(ADMIN)
                                // auth
                                .requestMatchers("/api/v1/auth/**").permitAll()
                                // order detail
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/orderDetail/**").hasAnyRole(ADMIN)
                                // user
                                .requestMatchers("/api/v1/user/search").permitAll()
                                .requestMatchers("/api/v1/user/search/paginated").permitAll()
                                .requestMatchers("/api/v1/user/**").hasAnyRole(ADMIN)
                                // variation
                                .requestMatchers("/api/v1/variationValue/variation").hasAnyRole(ADMIN, INVENTORY_MANAGER)
                                // mail
                                .requestMatchers("/api/v1/mail/**").permitAll()
                                // role
                                .requestMatchers("/api/v1/role/**").hasAnyRole(ADMIN)
                                // rating
                                .requestMatchers("/api/v1/rating/**").permitAll()
                                // product
                                .requestMatchers("/api/v1/product/category").permitAll()
                                .requestMatchers("/api/v1/product/search").permitAll()
                                .requestMatchers("/api/v1/product/search/paginated").permitAll()
                                .requestMatchers("/api/v1/product").hasAnyRole(ADMIN, INVENTORY_MANAGER)
                                .requestMatchers("/api/v1/product/**").hasAnyRole(ADMIN, INVENTORY_MANAGER)
                                // all
                                .anyRequest().permitAll())
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}

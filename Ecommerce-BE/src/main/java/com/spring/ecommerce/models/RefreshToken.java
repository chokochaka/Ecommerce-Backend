package com.spring.ecommerce.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "refresh_token")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class RefreshToken {
    @Id
    @GeneratedValue
    private Long id;

    private String refreshToken;

    private Instant issuedAt;
    private Instant expiresAt;

    @OneToOne
    private User user;
}

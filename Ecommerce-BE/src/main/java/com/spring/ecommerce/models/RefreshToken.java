package com.spring.ecommerce.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

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

    @Column(updatable = false, nullable = false)
    @CreatedDate
    private Instant issuedAt;

    @Column(updatable = false, nullable = false)
    private Instant expiresAt;

    @OneToOne
    private User user;
}

package com.spring.ecommerce.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;

@Entity
@Table(name = "refresh_token")
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
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

    @OneToOne(fetch = FetchType.LAZY)
    private User user;
}

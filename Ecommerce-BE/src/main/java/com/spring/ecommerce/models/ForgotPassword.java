package com.spring.ecommerce.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "forgot_password")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class ForgotPassword {
    @Id
    @GeneratedValue
    private Long id;

    private Integer otp;
    private Instant expiresAt;

    @OneToOne
    private User user;
}

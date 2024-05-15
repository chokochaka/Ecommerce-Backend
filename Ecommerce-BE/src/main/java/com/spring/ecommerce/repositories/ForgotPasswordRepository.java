package com.spring.ecommerce.repositories;

import com.spring.ecommerce.models.ForgotPassword;
import com.spring.ecommerce.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Long> {
    @Query("SELECT f FROM ForgotPassword f WHERE f.otp = ?1 AND f.user = ?2")
    Optional<ForgotPassword> findByOtpAndUser(Integer otp, User user);
}

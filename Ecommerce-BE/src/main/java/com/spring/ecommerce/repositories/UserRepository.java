package com.spring.ecommerce.repositories;

import com.spring.ecommerce.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("update User u set u.password = ?2 where u.email = ?1")
    void updatePassword(String email, String password);

    @Transactional
    @Modifying
    @Query("update User u set u.verificationCode = ?2 where u.email = ?1")
    void updateVerificationCode(String email, String verificationCode);
}

package com.spring.ecommerce.repositories;

import com.spring.ecommerce.models.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private User user;

    @BeforeEach
    public void setup() {
        // Setup user
        user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password")
                .enabled(true)
                .build();
        user = userRepository.save(user);
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }

    @DisplayName("JUnit test for findByEmail")
    @Test
    public void findByEmail_WhenEmailExists_ReturnsUser() {
        // When
        Optional<User> foundUser = userRepository.findByEmail("john.doe@example.com");

        // Then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("john.doe@example.com");
    }

    @DisplayName("JUnit test for findByEmail when email does not exist")
    @Test
    public void findByEmail_WhenEmailDoesNotExist_ReturnsEmpty() {
        // When
        Optional<User> foundUser = userRepository.findByEmail("nonexistent@example.com");

        // Then
        assertThat(foundUser).isNotPresent();
    }

    @DisplayName("JUnit test for updatePassword")
    @Test
    @Transactional
    public void updatePassword_WhenEmailExists_ShouldUpdatePassword() {
        // Given
        String newPassword = "newpassword";

        // When
        userRepository.updatePassword(user.getEmail(), newPassword);
        testEntityManager.flush();  // flushed to the database
        testEntityManager.clear();  // clear the persistence context to ensure we fetch new data

        // Then
        Optional<User> updatedUser = userRepository.findByEmail(user.getEmail());
        assertThat(updatedUser).isPresent();
        assertThat(updatedUser.get().getPassword()).isEqualTo(newPassword);
    }

    @DisplayName("JUnit test for updateVerificationCode")
    @Test
    @Transactional
    public void updateVerificationCode_WhenEmailExists_ShouldUpdateVerificationCode() {
        // Given
        String newVerificationCode = "newcode";

        // When
        userRepository.updateVerificationCode("john.doe@example.com", newVerificationCode);
        testEntityManager.flush();
        testEntityManager.clear();

        // Then
        Optional<User> updatedUser = userRepository.findByEmail("john.doe@example.com");
        assertThat(updatedUser).isPresent();
        assertThat(updatedUser.get().getVerificationCode()).isEqualTo(newVerificationCode);
    }
}

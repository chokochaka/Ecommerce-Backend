package com.spring.ecommerce.repositories;

import com.spring.ecommerce.models.Address;
import com.spring.ecommerce.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AddressRepositoryTests {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    private Address address;
    private User user;

    @BeforeEach
    public void setup() {
        // setup user
        user = User.builder()
                .firstName("Nash")
                .lastName("Tech")
                .email("nash@gmail.com")
                .enabled(true)
                .build();


        // setup address
        address = Address.builder()
                .address("address line 1")
                .city("city")
                .province("state")
                .country("country")
                .user(user)
                .build();

        user.setAddress(address);
        user = userRepository.save(user);
    }

    @DisplayName("JUnit test for get address by user id")
    @Test
    public void findByUserId_WhenUserExists_ReturnsAddress() {
        // when
        Address foundAddress = addressRepository.findByUser_Id(user.getId());

        // then
        assertThat(foundAddress).isNotNull();
        assertThat(foundAddress.getUser().getId()).isEqualTo(user.getId());
        assertThat(foundAddress.getAddress()).isEqualTo(address.getAddress());
    }

    @DisplayName("JUnit test for get address by user id when user does not exist")
    @Test
    public void findByUserId_WhenUserDoesNotExist_ReturnsNull() {
        // when
        Address foundAddress = addressRepository.findByUser_Id(-1L); // Using an ID that does not exist

        // then
        assertThat(foundAddress).isNull();
    }
}

package com.spring.ecommerce.services;

import com.spring.ecommerce.dto.AddressDto;
import com.spring.ecommerce.models.Address;
import com.spring.ecommerce.models.User;
import com.spring.ecommerce.repositories.AddressRepository;
import com.spring.ecommerce.repositories.UserRepository;
import com.spring.ecommerce.services.impl.AddressServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTests {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AddressServiceImpl addressService;


    private User user;
    private Address address;
    private AddressDto addressDto;

    @BeforeEach
    public void setup() {
//        MockitoAnnotations.openMocks(this);

        user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password")
                .enabled(true)
                .build();
        user.setId(1L);

        address = Address.builder()
                .country("Country")
                .province("Province")
                .city("City")
                .address("123 Main St")
                .user(user)
                .build();
        address.setId(1L);

        addressDto = AddressDto.builder()
                .id(1L)
                .country("Country")
                .province("Province")
                .city("City")
                .address("123 Main St")
                .userId(1L)
                .build();
    }

    @AfterEach
    public void tearDown() {
        reset(addressRepository, userRepository);
    }


    @DisplayName("JUnit test for addAddress method")
    @Test
    public void addAddress_ShouldSaveAddress() {
        // Given
        when(userRepository.findById(addressDto.getUserId())).thenReturn(Optional.of(user));

        // When
        addressService.addAddress(addressDto);

        // Then
        ArgumentCaptor<Address> addressArgumentCaptor = ArgumentCaptor.forClass(Address.class);
        verify(addressRepository, times(1)).save(addressArgumentCaptor.capture());
        Address savedAddress = addressArgumentCaptor.getValue();

        assertThat(savedAddress.getCountry()).isEqualTo(addressDto.getCountry());
        assertThat(savedAddress.getProvince()).isEqualTo(addressDto.getProvince());
        assertThat(savedAddress.getCity()).isEqualTo(addressDto.getCity());
        assertThat(savedAddress.getAddress()).isEqualTo(addressDto.getAddress());
        assertThat(savedAddress.getUser().getId()).isEqualTo(addressDto.getUserId());
    }

    @DisplayName("JUnit test for updateAddress method")
    @Test
    public void updateAddress_ShouldUpdateExistingAddress() {
        // Given
        when(addressRepository.findById(addressDto.getId())).thenReturn(Optional.of(address));

        // When
        addressService.updateAddress(addressDto);

        // Then
        ArgumentCaptor<Address> addressArgumentCaptor = ArgumentCaptor.forClass(Address.class);
        verify(addressRepository, times(1)).save(addressArgumentCaptor.capture());
        Address updatedAddress = addressArgumentCaptor.getValue();

        assertThat(updatedAddress.getCountry()).isEqualTo(addressDto.getCountry());
        assertThat(updatedAddress.getProvince()).isEqualTo(addressDto.getProvince());
        assertThat(updatedAddress.getCity()).isEqualTo(addressDto.getCity());
        assertThat(updatedAddress.getAddress()).isEqualTo(addressDto.getAddress());
    }

    @DisplayName("JUnit test for getAddressByUserId method")
    @Test
    public void getAddressByUserId_ShouldReturnAddressDto() {
        // Given
        when(addressRepository.findByUser_Id(user.getId())).thenReturn(address);

        // When
        AddressDto foundAddressDto = addressService.getAddressByUserId(user.getId());

        // Then
        assertThat(foundAddressDto).isNotNull();
        assertThat(foundAddressDto.getId()).isEqualTo(address.getId());
        assertThat(foundAddressDto.getCountry()).isEqualTo(address.getCountry());
        assertThat(foundAddressDto.getProvince()).isEqualTo(address.getProvince());
        assertThat(foundAddressDto.getCity()).isEqualTo(address.getCity());
        assertThat(foundAddressDto.getAddress()).isEqualTo(address.getAddress());
        assertThat(foundAddressDto.getUserId()).isEqualTo(address.getUser().getId());
    }
}
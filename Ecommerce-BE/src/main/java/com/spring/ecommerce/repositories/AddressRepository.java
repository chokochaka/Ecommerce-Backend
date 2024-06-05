package com.spring.ecommerce.repositories;

import com.spring.ecommerce.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Address findByUser_Id(Long id); // nested property
}

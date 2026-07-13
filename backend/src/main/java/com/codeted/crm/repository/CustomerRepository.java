package com.codeted.crm.repository;

import com.codeted.crm.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByPublicId(UUID publicId);
}

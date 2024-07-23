package com.udemy.security.repository;

import com.udemy.security.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

  Optional<Customer> findByEmail(String email);
}
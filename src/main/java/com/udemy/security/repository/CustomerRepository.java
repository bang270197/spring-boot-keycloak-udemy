package com.udemy.security.repository;

import com.udemy.security.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

  List<Customer> findByEmail(String email);
}
package com.udemy.security.repository;

import com.udemy.security.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

  Optional<Account> findByCustomeId(Integer customeId);
}
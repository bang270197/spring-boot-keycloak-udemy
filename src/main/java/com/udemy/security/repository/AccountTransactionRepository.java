package com.udemy.security.repository;

import com.udemy.security.entity.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, String> {

  List<AccountTransaction> findByCustomerIdOrderByCreateDtDesc(Integer customerId);
}
package com.udemy.security.repository;

import com.udemy.security.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Integer> {

    List<Loan> findByCustomerIdOrderByCreateDtDesc(Integer customerId);
}
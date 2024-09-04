package com.udemy.security.controller;

import com.udemy.security.entity.Customer;
import com.udemy.security.entity.Loan;
import com.udemy.security.repository.CustomerRepository;
import com.udemy.security.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class LoansController {

    private final LoanRepository loanRepository;
    private final CustomerRepository customerRepository;

    @GetMapping("myLoans")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Loan> getLoansDetail(@RequestParam String email){
        Optional<Customer> customerOpt = customerRepository.findByEmail(email);
        if(customerOpt.isPresent()){
            Customer customer = customerOpt.get();
            List<Loan> loans = loanRepository.findByCustomerIdOrderByCreateDtDesc(customer.getCustomeId());
            if (!loans.isEmpty()){
                return loans;
            } else {
                return null;
            }
        } else {
            return null;
        }

    }
}

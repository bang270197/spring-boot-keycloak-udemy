package com.udemy.security.controller;

import com.udemy.security.entity.AccountTransaction;
import com.udemy.security.entity.Customer;
import com.udemy.security.repository.AccountTransactionRepository;
import com.udemy.security.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
public class BalanceController {
    private final AccountTransactionRepository accountTransactionRepository;
    private final CustomerRepository customerRepository;

    @GetMapping("myBalance")
    public List<AccountTransaction> getBalanceDetail(@RequestParam String email){
        Optional<Customer> customerOpt = customerRepository.findByEmail(email);
        if (customerOpt.isPresent()){
            Customer customer = customerOpt.get();
            List<AccountTransaction> accountTransactions = accountTransactionRepository.findByCustomerIdOrderByCreateDtDesc(customer.getCustomeId());
            if (!accountTransactions.isEmpty()){
                return accountTransactions;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}

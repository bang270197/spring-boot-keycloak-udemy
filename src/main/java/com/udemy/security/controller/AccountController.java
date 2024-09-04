package com.udemy.security.controller;

import com.udemy.security.entity.Account;
import com.udemy.security.entity.Customer;
import com.udemy.security.repository.AccountRepository;
import com.udemy.security.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class AccountController {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    @GetMapping("myAccount")
    public Account getAccount(@RequestParam String email){
        Optional<Customer> customerOpt = customerRepository.findByEmail(email);
        if (customerOpt.isPresent()){
            Customer customer = customerOpt.get();
            Optional<Account> account = accountRepository.findByCustomeId(customer.getCustomeId());
            if (account.isPresent()){
                return account.get();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}

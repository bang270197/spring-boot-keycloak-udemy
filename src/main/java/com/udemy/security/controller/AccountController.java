package com.udemy.security.controller;

import com.udemy.security.entity.Account;
import com.udemy.security.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/myAccount")
@RequiredArgsConstructor
public class AccountController {
    private final AccountRepository accountRepository;

    @GetMapping("")
    public Account getAccount(@RequestParam("customerId") Long id){
        Account account = accountRepository.getById(id);
        if (account != null){
            return account;
        } else {
            return null;
        }
    }
}

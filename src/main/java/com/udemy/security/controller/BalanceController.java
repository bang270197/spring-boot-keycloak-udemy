package com.udemy.security.controller;

import com.udemy.security.entity.AccountTransaction;
import com.udemy.security.repository.AccountRepository;
import com.udemy.security.repository.AccountTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/myBalance")
public class BalanceController {
    private final AccountTransactionRepository accountTransactionRepository;

    @GetMapping("")
    public List<AccountTransaction> getBalanceDetail(@RequestParam("customerId") Integer id){
        List<AccountTransaction> accountTransactions = accountTransactionRepository.findByCustomerIdOrderByCreateDtDesc(id);
        if (!accountTransactions.isEmpty()){
            return accountTransactions;
        } else {
            return null;
        }
    }
}

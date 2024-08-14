package com.udemy.security.controller;

import com.udemy.security.entity.Loan;
import com.udemy.security.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/myLoans")
public class LoansController {

    private final LoanRepository loanRepository;

    @GetMapping("")
    public List<Loan> getLoansDetail(@RequestParam("id") Integer id){
        List<Loan> loans = loanRepository.findByCustomerIdOrderByCreateDtDesc(id);
        if (!loans.isEmpty()){
            return loans;
        } else {
            return null;
        }
    }
}

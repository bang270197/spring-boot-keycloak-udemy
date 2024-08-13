package com.udemy.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/")
@RestController
public class AuthenticateController {

    @GetMapping("/myAccount")
    public String getAccountDetails() {
        return
                "Here are the account details from the DB";
    }

    @GetMapping("/myBalance")
    public String getBalanceDetails() {
        return "Here are the balance details from the DB";
    }

    @GetMapping("/myLoans")
    public String getLoansDetails() {
        return "Here are the loans details from the DB";
    }

    @GetMapping("/myCards")
    public String getCardsDetail() {
        return "Here are the card details from the DB";
    }
}

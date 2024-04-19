package com.udemy.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PermitAllController {
    @GetMapping("/notices")
    public String getNotiesDetails() {
        return "Here are the noties details from the DB";
    }

    @GetMapping("/contact")
    public String getContactDetails() {
        return "Here are the contact details from the DB";
    }

    @GetMapping("/register")
    public String getRegisterDetails() {
        return "Here are the register details from the DB";
    }
}

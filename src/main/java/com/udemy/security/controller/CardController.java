package com.udemy.security.controller;

import com.udemy.security.entity.Card;
import com.udemy.security.entity.Customer;
import com.udemy.security.repository.CardRepository;
import com.udemy.security.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class CardController {
    private final CardRepository cardRepository;
    private final CustomerRepository customerRepository;

    @GetMapping("myCard")
    public List<Card> getCard(@RequestParam String email){
        Optional<Customer> customer = customerRepository.findByEmail(email);
        if (customer.isPresent()) {
            Customer custom = customer.get();
            List<Card> cards = cardRepository.findByCustomeId(custom.getCustomeId());
            if (!cards.isEmpty()){
                return cards;
            } else {
                return null;
            }
        }else {
            return null;
        }

    }
}

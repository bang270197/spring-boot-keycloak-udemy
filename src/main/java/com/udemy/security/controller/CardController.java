package com.udemy.security.controller;

import com.udemy.security.entity.Card;
import com.udemy.security.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/myCard")
public class CardController {
    private final CardRepository cardRepository;

    public List<Card> getCard(@RequestParam("customerId") Integer id){
        List<Card> cards = cardRepository.findByCustomeId(id);
        if (!cards.isEmpty()){
            return cards;
        } else {
            return null;
        }
    }
}

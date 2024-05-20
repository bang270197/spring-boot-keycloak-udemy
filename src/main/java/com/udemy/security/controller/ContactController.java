package com.udemy.security.controller;

import com.udemy.security.entity.ContactMessage;
import com.udemy.security.repository.ContactMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequiredArgsConstructor
@RequestMapping("/myContact")
public class ContactController {

    private final ContactMessageRepository contactMessageRepository;

    @PostMapping("")
    public ContactMessage saveContactInquiryDetails(@RequestBody ContactMessage contactMessage){
        contactMessage.setContactId(genRqNumber());
//        contactMessage.setCreateDt();
        return contactMessageRepository.save(contactMessage);
    }

    private String genRqNumber(){
        Random random = new Random();
        int randomNum = random.nextInt(999999999 - 9999) + 9999;
        return "SR" + randomNum;
    }
}

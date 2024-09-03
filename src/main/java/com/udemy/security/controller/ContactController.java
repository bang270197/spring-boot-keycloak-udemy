package com.udemy.security.controller;

import com.udemy.security.entity.ContactMessage;
import com.udemy.security.repository.ContactMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class ContactController {

    private final ContactMessageRepository contactMessageRepository;

    //là một chú thích (annotation) trong Spring Security được sử dụng để lọc các phần tử của một tập hợp (collection)
    // đầu vào trước khi phương thức được thực thi
    @PreFilter("filterObject.contactName != 'Test'")
    @PostMapping("myContact")
    public ContactMessage saveContactInquiryDetails(@RequestBody List<ContactMessage> contactMessages){
        ContactMessage contactMessage = contactMessages.getFirst();
        if (null != contactMessage){
            contactMessage.setContactId(genRqNumber());
            return contactMessageRepository.save(contactMessage);
        }
        return null;

    }

    private String genRqNumber(){
        Random random = new Random();
        int randomNum = random.nextInt(999999999 - 9999) + 9999;
        return "SR" + randomNum;
    }
}

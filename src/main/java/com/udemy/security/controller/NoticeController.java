package com.udemy.security.controller;

import com.udemy.security.entity.NoticeDetail;
import com.udemy.security.repository.NoticeDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
@RequestMapping("/myNotice")
public class NoticeController {
    private final NoticeDetailRepository noticeDetailRepository;

    @GetMapping("")
    public ResponseEntity<List<NoticeDetail>> getNotice(){

        List<NoticeDetail> noticeDetails = noticeDetailRepository.findAll();
        if (!noticeDetails.isEmpty()){
            return ResponseEntity.ok().cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
                    .body(noticeDetails);
        } else {
            return null;
        }
    }
}

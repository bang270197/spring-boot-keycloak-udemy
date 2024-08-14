package com.udemy.security.controller;

import com.udemy.security.entity.NoticeDetail;
import com.udemy.security.repository.NoticeDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class NoticeController {
    private final NoticeDetailRepository noticeDetailRepository;

    @PostAuthorize("hasRole('USER')")//Dùng để kiểm tra điều kiện sau khi thực thi phương thức
    //Kiểm tra đầu ra phải là user đăng nhập
//    @PostAuthorize("returnObject.username = authentication.principal.username")

        //Loc danh sách đầu ra
//    @PostAuthorize("filterObject.username = 'abc'")
    @GetMapping("myNotice")
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

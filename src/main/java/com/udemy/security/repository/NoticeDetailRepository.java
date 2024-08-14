package com.udemy.security.repository;

import com.udemy.security.entity.NoticeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface NoticeDetailRepository extends JpaRepository<NoticeDetail, Integer> {

//    @PreAuthorize("hasRole('USER')")//Dùng để kiểm tra điều kiện trước khi thực thi phương thức.
//    Thường sử dụng cho các điều kiện quyền truy cập
//Kiểm tra đầu vào phải là user của tk đăng nập
//    @PreAuthorize("#userName == authentication.principal.username")
    @Query(value = "from NoticeDetail n where n.createDt between n.noticBegDt and n.noticEndDt")
    List<NoticeDetail> findAll();
}
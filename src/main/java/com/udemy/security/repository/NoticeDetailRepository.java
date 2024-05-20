package com.udemy.security.repository;

import com.udemy.security.entity.NoticeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoticeDetailRepository extends JpaRepository<NoticeDetail, Integer> {

    @Query(value = "from NoticeDetail n where n.createDt between n.noticBegDt and n.noticEndDt")
    List<NoticeDetail> findAll();
}
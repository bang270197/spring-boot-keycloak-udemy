package com.udemy.security.repository;

import com.udemy.security.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Integer> {

  List<Card> findByCustomeId(Integer customeId);
}
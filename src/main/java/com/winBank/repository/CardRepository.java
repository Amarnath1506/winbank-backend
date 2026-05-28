package com.winbank.repository;

import com.winbank.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByOwnerEmail(String ownerEmail);
}
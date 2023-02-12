package com.example.bilingual.db.repository;

import com.example.bilingual.db.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository<Result, Long> {
}
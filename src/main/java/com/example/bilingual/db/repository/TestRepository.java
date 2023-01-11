package com.example.bilingual.db.repository;

import com.example.bilingual.db.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {
}
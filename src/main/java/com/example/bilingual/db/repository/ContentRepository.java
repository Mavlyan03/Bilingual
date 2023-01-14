package com.example.bilingual.db.repository;

import com.example.bilingual.db.model.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {
}
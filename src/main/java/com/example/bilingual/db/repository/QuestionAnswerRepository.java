package com.example.bilingual.db.repository;

import com.example.bilingual.db.model.QuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer, Long> {
}
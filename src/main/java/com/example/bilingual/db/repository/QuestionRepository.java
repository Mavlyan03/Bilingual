package com.example.bilingual.db.repository;

import com.example.bilingual.db.model.Question;
import com.example.bilingual.dto.response.QuestionTestResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("select new com.example.bilingual.dto.response.QuestionTestResponse(" +
            "q.id," +
            "q.title," +
            "q.duration," +
            "q.questionType," +
            "q.isActive) from Question q where q.test.id = :id")
    List<QuestionTestResponse> getAllQuestionsByTestId(Long id);
}
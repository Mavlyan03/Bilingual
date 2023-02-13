package com.example.bilingual.db.repository;

import com.example.bilingual.db.model.QuestionAnswer;
import com.example.bilingual.dto.response.QuestionAnswerResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer, Long> {

    @Query("select new com.example.bilingual.dto.response.QuestionAnswerResponse(" +
            "q.id," +
            "q.question.title," +
            "q.score," +
            "q.status," +
            "q.seen) from QuestionAnswer q where q.result.id = ?1")
    List<QuestionAnswerResponse> getAllQuestionAnswerByResultId(Long resultId);
}
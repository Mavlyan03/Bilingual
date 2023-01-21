package com.example.bilingual.db.repository;

import com.example.bilingual.db.model.Question;
import com.example.bilingual.db.model.enums.QuestionType;
import com.example.bilingual.dto.response.QuestionTestResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("select new com.example.bilingual.dto.response.QuestionTestResponse(" +
            "q.id," +
            "q.title," +
            "q.duration," +
            "q.questionType," +
            "q.isActive) from Question q where q.test.id = :id")
    List<QuestionTestResponse> getAllQuestionsByTestId(Long id);

    @Modifying
    @Transactional
    @Query("update Question q set " +
            "q.title = :title, " +
            "q.duration = :duration, " +
            "q.statement = :statement, " +
            "q.passage = :passage, " +
            "q.correctAnswer = :correct, " +
            "q.content = :content, " +
            "q.numberOfReplays = :replays, " +
            "q.minWords = :minWords  where q.id = :id")
    void updateQuestion(@Param("id") Long id,
                        @Param("title") String title,
                        @Param("duration") Integer duration,
                        @Param("statement") String statement,
                        @Param("passage") String passage,
                        @Param("correct") String correctAnswer,
                        @Param("content") String content,
                        @Param("replays") Integer numberOfReplays,
                        @Param("minWords") Integer minNumberOfWords);
}
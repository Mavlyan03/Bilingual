package com.example.bilingual.db.service;

import com.example.bilingual.db.model.QuestionAnswer;
import com.example.bilingual.db.model.Result;
import com.example.bilingual.db.repository.QuestionAnswerRepository;
import com.example.bilingual.db.repository.ResultRepository;
import com.example.bilingual.dto.request.ScoreRequest;
import com.example.bilingual.dto.response.ResultResponse;
import com.example.bilingual.dto.response.ViewResultResponse;
import com.example.bilingual.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class ResultServiceTest {

    @Autowired
    private ResultService resultService;

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private QuestionAnswerRepository answerRepository;

    @Test
    void getAllResults() {
        List<ResultResponse> results = resultService.getAllResults();

        assertNotNull(results);
        assertEquals(results.size(), 1);
    }

    @Test
    void giveScoreForQuestion() {
        ScoreRequest request = new ScoreRequest();
        request.setQuestionId(1L);
        request.setScore(4.0f);

        float before = request.getScore();

        ViewResultResponse viewResult = resultService.giveScoreForQuestion(request);
        QuestionAnswer questionAnswer = answerRepository.findById(1L).orElseThrow();

        float after = questionAnswer.getScore();

        assertNotNull(viewResult);
        assertEquals(before, after);
    }

    @Test
    void deleteResult() {
        List<ResultResponse> result = resultService.deleteResult(1L);

        assertNotNull(result);
        assertThatThrownBy(() -> resultService.getResultById(1L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Result not found");
    }

    @Test
    void getResultById() {
        Result result1 = resultRepository.findById(1L).orElseThrow();
        ViewResultResponse result = resultService.getResultById(1L);

        assertNotNull(result);
        assertEquals(result.getId(), result1.getId());
        assertEquals(result.getTest(), result1.getTest().getTitle());
        assertEquals(result.getFinalScore(), result1.getScore());
        assertEquals(result.getFinalStatus(), result1.getStatus());
        assertEquals(result.getDateOfSubmission(), result1.getDateOfSubmission());
    }

    @Test
    void getAnswer() {
    }
}
package com.example.bilingual.db.service;

import com.example.bilingual.db.model.Result;
import com.example.bilingual.db.repository.ResultRepository;
import com.example.bilingual.dto.response.ResultResponse;
import com.example.bilingual.dto.response.ViewResultResponse;
import com.example.bilingual.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ResultServiceTest {

    @Autowired
    private ResultService resultService;

    @Autowired
    private ResultRepository resultRepository;

    @Test
    void getAllResults() {
        List<ResultResponse> results = resultService.getAllResults();

        assertNotNull(results);
        assertEquals(results.size(), 1);
    }

    @Test
    void sendResultsToEmail() {
    }

    @Test
    void giveResultResponse() {
    }

    @Test
    void giveScoreForQuestion() {
    }

    @Test
    void getAllClientResults() {
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
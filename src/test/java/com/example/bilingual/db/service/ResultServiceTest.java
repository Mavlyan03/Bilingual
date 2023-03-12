package com.example.bilingual.db.service;

import com.example.bilingual.db.repository.ResultRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

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
    }

    @Test
    void delete() {
    }

    @Test
    void getResultById() {
    }

    @Test
    void getAnswer() {
    }
}
package com.example.bilingual.db.service;

import com.example.bilingual.db.repository.TestRepository;
import com.example.bilingual.dto.request.TestRequest;
import com.example.bilingual.dto.response.SimpleResponse;
import com.example.bilingual.dto.response.TestResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TestServiceTest {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private TestService testService;

    @Test
    void enableDisable() {
        com.example.bilingual.db.model.Test test = testRepository.findById(1L).orElseThrow();
        boolean before = test.getIsActive();

        SimpleResponse simpleResponse = testService.enableDisable(1L);

        com.example.bilingual.db.model.Test test1 = testRepository.findById(1L).orElseThrow();

        assertNotNull(simpleResponse);
        assertNotEquals(test1.getIsActive(), before);
    }

    @Test
    void saveTest() {
        TestRequest request = new TestRequest();
        request.setTitle("New York Time");
        request.setShortDescription("The weather in NYC is sunny today");

        TestResponse test = testService.saveTest(request);

        assertNotNull(test);
        assertEquals(request.getTitle(), test.getTitle());
        assertEquals(request.getShortDescription(), test.getShortDescription());
    }

    @Test
    void updateTest() {
        com.example.bilingual.db.model.Test test1 = testRepository.findById(1L).orElseThrow();

        TestRequest request = new TestRequest();
        request.setTitle("Chicago Bulls");
        request.setShortDescription("Chicago Bulls is the best team");

        TestResponse test = testService.updateTest(test1.getId(), request);

        assertNotNull(test);
        assertEquals(test.getTitle(), request.getTitle());
        assertEquals(test.getShortDescription(), request.getShortDescription());
        assertNotEquals(test1.getTitle(), test.getTitle());
        assertNotEquals(test1.getDescription(), test.getShortDescription());
    }

    @Test
    void deleteTest() {
    }

    @Test
    void getAllTests() {
    }

    @Test
    void getTestById() {
    }
}
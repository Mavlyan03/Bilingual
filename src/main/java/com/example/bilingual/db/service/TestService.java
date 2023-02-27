package com.example.bilingual.db.service;

import com.example.bilingual.db.model.Test;
import com.example.bilingual.db.repository.QuestionRepository;
import com.example.bilingual.db.repository.TestRepository;
import com.example.bilingual.dto.request.TestRequest;
import com.example.bilingual.dto.response.QuestionTestResponse;
import com.example.bilingual.dto.response.SimpleResponse;
import com.example.bilingual.dto.response.TestInnerPageResponse;
import com.example.bilingual.dto.response.TestResponse;
import com.example.bilingual.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestService {

    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;

    @Transactional
    public SimpleResponse enableDisable(Long id) {
        Test test = testRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Test not found"));
        test.setIsActive(!test.getIsActive());
        log.info("Switch test was successfully");
        if (test.getIsActive()) {
            return new SimpleResponse("Test is enable");
        } else {
            return new SimpleResponse("Test is disable");
        }
    }

    public TestResponse saveTest(TestRequest testRequest) {
        Test test = new Test(testRequest);
        testRepository.save(test);
        log.info("Test saved successfully");
        return new TestResponse(test);
    }

    public TestResponse updateTest(Long id, TestRequest testRequest) {
        Test test = testRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Test not found"));
        testRepository.updateTest(
                test.getId(),
                testRequest.getTitle(),
                testRequest.getShortDescription());
        log.info("Test updated successfully");
        return new TestResponse(
                id, testRequest.getTitle(),
                testRequest.getShortDescription(),
                test.getIsActive());
    }

    public SimpleResponse deleteTest(Long id) {
        Test test = testRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Test not found"));
        testRepository.delete(test);
        log.info("Test deleted successfully");
        return new SimpleResponse("Test deleted successfully");
    }

    public List<TestResponse> getAllTests() {
        log.info("Get all tests was successfully");
        return testRepository.getAllTests();
    }

    public TestInnerPageResponse getTestById(Long id) {
        Test test = testRepository.findById(id)
                .orElseThrow(() -> new NotFoundException
                (String.format("Test with id %s not found", id)));
        TestInnerPageResponse testInnerPageResponse = new TestInnerPageResponse(test);
        List<QuestionTestResponse> questions = questionRepository.getAllQuestionsByTestId(test.getId());
        testInnerPageResponse.setQuestionTestResponses(questions);
        log.info("Get test by id was successfully");
        return testInnerPageResponse;
    }
}

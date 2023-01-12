package com.example.bilingual.db.service;

import com.example.bilingual.db.model.Test;
import com.example.bilingual.db.repository.TestRepository;
import com.example.bilingual.dto.request.TestRequest;
import com.example.bilingual.dto.response.SimpleResponse;
import com.example.bilingual.dto.response.TestResponse;
import com.example.bilingual.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;

    public SimpleResponse enableDisable(Long id) {
        Test test = testRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Test not found"));
        test.setIsActive(!test.getIsActive());
        if(test.getIsActive()) {
            return new SimpleResponse("Test is enable");
        } else {
            return new SimpleResponse("Test is disable");
        }
    }
    public TestResponse saveTest(TestRequest testRequest) {
        Test test = new Test(testRequest);
        testRepository.save(test);
        return new TestResponse(test);
    }

    public TestResponse updateTest(Long id, TestRequest testRequest) {
        Test test = testRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Test not found"));
        testRepository.updateTest(
                test.getId(),
                testRequest.getTitle(),
                testRequest.getShortDescription());
        return new TestResponse(test);
    }
    
    public SimpleResponse deleteTest(Long id) {
        Test test = testRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Test not found"));
        testRepository.delete(test);
        return new SimpleResponse("Test deleted successfully");
    }

    public List<TestResponse> getAllTests() {
        return testRepository.getAllTests();
    }
}

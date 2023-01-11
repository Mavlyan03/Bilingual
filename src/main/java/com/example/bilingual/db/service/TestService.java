package com.example.bilingual.db.service;

import com.example.bilingual.db.model.Test;
import com.example.bilingual.db.repository.TestRepository;
import com.example.bilingual.dto.request.TestRequest;
import com.example.bilingual.dto.response.TestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;


    public TestResponse saveTest(TestRequest testRequest) {
        Test test = new Test(testRequest);
        testRepository.save(test);
        return new TestResponse(test);
    }
}

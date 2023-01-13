package com.example.bilingual.db.service;

import com.example.bilingual.db.repository.QuestionRepository;
import com.example.bilingual.db.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final TestRepository testRepository;

}

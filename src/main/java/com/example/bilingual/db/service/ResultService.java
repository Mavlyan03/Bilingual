package com.example.bilingual.db.service;

import com.example.bilingual.db.model.Result;
import com.example.bilingual.db.repository.QuestionAnswerRepository;
import com.example.bilingual.db.repository.ResultRepository;
import com.example.bilingual.db.repository.UserRepository;
import com.example.bilingual.dto.response.QuestionAnswerResponse;
import com.example.bilingual.dto.response.ResultResponse;
import com.example.bilingual.dto.response.SimpleResponse;
import com.example.bilingual.dto.response.ViewResultResponse;
import com.example.bilingual.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultService {

    private final ResultRepository resultRepository;
    private final QuestionAnswerRepository answerRepository;
    private final UserRepository userRepository;

    public List<ResultResponse> getAllResults() {
        return resultRepository.getAllResults();
    }

    public SimpleResponse deleteResult(Long id) {
        Result result = resultRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Result not found"));
        result.setTest(null);
        result.setClient(null);
        resultRepository.delete(result);
        return new SimpleResponse("Result deleted successfully");
    }

    public ViewResultResponse getResultById(Long id) {
        ViewResultResponse result = resultRepository.getResultById(id).
                orElseThrow(() -> new NotFoundException("Result not found"));
        List<QuestionAnswerResponse> questions = answerRepository.getAllQuestionAnswerByResultId(id);
        result.setQuestions(questions);
        return result;
    }
}

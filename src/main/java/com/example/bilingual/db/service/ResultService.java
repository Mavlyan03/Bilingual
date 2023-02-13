package com.example.bilingual.db.service;

import com.example.bilingual.db.model.Client;
import com.example.bilingual.db.model.Result;
import com.example.bilingual.db.model.User;
import com.example.bilingual.db.repository.ClientRepository;
import com.example.bilingual.db.repository.QuestionAnswerRepository;
import com.example.bilingual.db.repository.ResultRepository;
import com.example.bilingual.dto.response.*;
import com.example.bilingual.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultService {

    private final ResultRepository resultRepository;
    private final QuestionAnswerRepository answerRepository;
    private final ClientRepository clientRepository;

    public List<ResultResponse> getAllResults() {
        return resultRepository.getAllResults();
    }

    public List<ClientResultResponse> getAllClientResults(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Client client = clientRepository.findClientByUserEmail(user.getEmail()).
                orElseThrow(() -> new NotFoundException("Client not found"));
        return resultRepository.getAllClientResults(client.getId());
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

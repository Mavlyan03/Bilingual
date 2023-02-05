package com.example.bilingual.db.service;

import com.example.bilingual.db.model.*;
import com.example.bilingual.db.model.enums.QuestionType;
import com.example.bilingual.db.model.enums.Status;
import com.example.bilingual.db.repository.*;
import com.example.bilingual.dto.request.PassTestRequest;
import com.example.bilingual.dto.request.QuestionAnswerRequest;
import com.example.bilingual.dto.response.SimpleResponse;
import com.example.bilingual.exception.BadRequestException;
import com.example.bilingual.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PassTestService {

    private final UserRepository userRepository;
    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;
    private final ContentRepository contentRepository;
    private final ClientRepository clientRepository;

    public SimpleResponse passTest(PassTestRequest testRequest, Principal principal) {
        Result result = new Result();
        Client client = clientRepository.findClientByUserEmail(principal.getName()).
                orElseThrow(() -> new NotFoundException("Client not found"));
        Test test = testRepository.findById(testRequest.getTestId()).
                orElseThrow(() -> new NotFoundException("Test not found"));
        result.setClient(client);
        result.setTest(test);
        result.setDateOfSubmission(LocalDateTime.now());
        result.setStatus(Status.NOT_EVALUATED);
        List<QuestionAnswer> answers = new ArrayList<>();
        for (QuestionAnswerRequest questions : testRequest.getQuestions()) {
            Question question = questionRepository.findById(questions.getQuestionId()).
                    orElseThrow(() -> new NotFoundException("Question not found"));
            QuestionAnswer answer = new QuestionAnswer();
            if (question.getQuestionType().equals(QuestionType.SELECT_THE_REAL_ENGLISH_WORDS) ||
                    question.getQuestionType().equals(QuestionType.LISTEN_AND_SELECT_ENGLISH_WORDS) ||
                    question.getQuestionType().equals(QuestionType.SELECT_THE_BEST_TITLE) ||
                    question.getQuestionType().equals(QuestionType.SELECT_THE_MAIN_IDEA)) {
                if (question.getQuestionType().equals(QuestionType.SELECT_THE_REAL_ENGLISH_WORDS) ||
                        question.getQuestionType().equals(QuestionType.LISTEN_AND_SELECT_ENGLISH_WORDS)) {
                    if(questions.getOptions().isEmpty() || question.getOptions() == null) {
                        throw new BadRequestException("Choose at least one option");
                    }
                }
            }
        }
        return null;
    }
}

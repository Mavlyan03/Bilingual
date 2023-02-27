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
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class PassTestService {
    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;
    private final ResultRepository resultRepository;
    private final QuestionAnswerRepository answerRepository;
    private final ClientRepository clientRepository;

    @Transactional
    public SimpleResponse passTest(PassTestRequest testRequest, Authentication authentication) {
        Result result = new Result();
        User user = (User) authentication.getPrincipal();
        Client client = clientRepository.findClientByUserEmail(user.getEmail()).
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
            if (question.getQuestionType().equals(QuestionType.SELECT_THE_REAL_ENGLISH_WORDS) ||
                    question.getQuestionType().equals(QuestionType.LISTEN_AND_SELECT_ENGLISH_WORDS) ||
                    question.getQuestionType().equals(QuestionType.SELECT_THE_BEST_TITLE) ||
                    question.getQuestionType().equals(QuestionType.SELECT_THE_MAIN_IDEA)) {
                if (question.getQuestionType().equals(QuestionType.SELECT_THE_REAL_ENGLISH_WORDS) ||
                        question.getQuestionType().equals(QuestionType.LISTEN_AND_SELECT_ENGLISH_WORDS)) {
                    if (questions.getOptions().isEmpty() || question.getOptions() == null) {
                        throw new BadRequestException("Choose at least one option");
                    }
                } else if (question.getQuestionType().equals(QuestionType.SELECT_THE_MAIN_IDEA) ||
                        question.getQuestionType().equals(QuestionType.SELECT_THE_BEST_TITLE)) {
                    if (questions.getOptions().isEmpty() || questions.getOptions() == null) {
                        throw new BadRequestException("Choose one correct option");
                    }
                }
                Set<Option> correctOptions = new HashSet<>();
                for (Option option : question.getOptions()) {
                    if (option.getIsTrue().equals(true)) {
                        correctOptions.add(option);
                    }
                }
                float score = 10f / correctOptions.size();
                int countOfCorrectOptions = 0;
                int countOfIncorrectOptions = 0;
                Set<Option> options = new HashSet<>();
                for (Long id : questions.getOptions()) {
                    Option option = optionRepository.findById(id).orElseThrow(
                            () -> new NotFoundException("Option not found"));
                    if (option.getIsTrue().equals(true)) {
                        countOfCorrectOptions++;
                    } else {
                        countOfIncorrectOptions++;
                    }
                    options.add(option);
                }
                QuestionAnswer questionAnswer;
                if (countOfCorrectOptions - countOfIncorrectOptions <= 0) {
                    questionAnswer = new QuestionAnswer(0f, question, options, result, false,
                            Status.NOT_EVALUATED, question.getContent());
                } else {
                    questionAnswer = new QuestionAnswer(
                            (countOfCorrectOptions - countOfIncorrectOptions) * score,
                            question, options, result, false, Status.NOT_EVALUATED, question.getContent());
                }
                answers.add(questionAnswer);
                answerRepository.save(questionAnswer);
            } else if (question.getQuestionType().equals(QuestionType.TYPE_WHAT_YOU_HEAR) ||
                    question.getQuestionType().equals(QuestionType.DESCRIBE_IMAGE) ||
                    question.getQuestionType().equals(QuestionType.HIGHLIGHT_THE_ANSWER)) {
                QuestionAnswer questionAnswer;
                if (questions.getAnswer().isEmpty() || questions.getAnswer() == null) {
                    throw new BadRequestException("Answer shouldn't be empty!");
                } else if (!questions.getAnswer().equals(question.getCorrectAnswer())) {
                    questionAnswer = new QuestionAnswer(0f, question, result, false, Status.NOT_EVALUATED,
                            question.getContent(), questions.getAnswer(), questions.getNumberOfReplays());
                } else {
                    questionAnswer = new QuestionAnswer(10f, question, result, false, Status.NOT_EVALUATED,
                            question.getContent(), questions.getAnswer(), questions.getNumberOfReplays());
                }
                answers.add(questionAnswer);
                answerRepository.save(questionAnswer);

            } else if (question.getQuestionType().equals(QuestionType.RESPOND_IN_AT_LEAST_N_WORDS)) {
                if (questions.getAnswer().isEmpty() || questions.getAnswer() == null) {
                    throw new BadRequestException("Response shouldn't be empty!");
                } else {
                    String[] word = questions.getAnswer().split(" ");
                    QuestionAnswer questionAnswer;
                    if (!(word.length >= question.getMinWords())) {
                        questionAnswer = new QuestionAnswer(0f, question, result, false,
                                Status.NOT_EVALUATED, question.getContent(), word.length);
                        questionAnswer.setTextResponseUser(questions.getAnswer());
                    } else {
                        questionAnswer = new QuestionAnswer(10f, question, result, false,
                                Status.NOT_EVALUATED, question.getContent(), word.length);
                        questionAnswer.setTextResponseUser(questions.getAnswer());
                    }
                    answers.add(questionAnswer);
                    answerRepository.save(questionAnswer);
                }
            } else if (question.getQuestionType().equals(QuestionType.RECORD_SAYING_STATEMENT)) {
                if (questions.getAnswer().isEmpty() || questions.getAnswer() == null) {
                    throw new BadRequestException("Record saying statement!");
                }
                QuestionAnswer questionAnswer;
                if (!questions.getAnswer().equals(question.getCorrectAnswer())) {
                    questionAnswer = new QuestionAnswer(0f, question, result, false, Status.NOT_EVALUATED,
                            question.getContent(), questions.getAnswer());
                } else {
                    questionAnswer = new QuestionAnswer(10f, question, result, false, Status.NOT_EVALUATED,
                            question.getContent(), questions.getAnswer());
                }
                answers.add(questionAnswer);
                answerRepository.save(questionAnswer);
            }
        }
        float finalScore = 0f;
        for (QuestionAnswer answer : answers) {
            finalScore += answer.getScore();
        }
        result.setScore(finalScore);
        resultRepository.save(result);
        log.info("Test passed successfully");
        return new SimpleResponse("Test is complete");
    }
}

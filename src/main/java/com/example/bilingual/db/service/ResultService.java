package com.example.bilingual.db.service;

import com.example.bilingual.db.model.*;
import com.example.bilingual.db.model.enums.QuestionType;
import com.example.bilingual.db.model.enums.Status;
import com.example.bilingual.db.repository.ClientRepository;
import com.example.bilingual.db.repository.QuestionAnswerRepository;
import com.example.bilingual.db.repository.QuestionRepository;
import com.example.bilingual.db.repository.ResultRepository;
import com.example.bilingual.dto.request.ScoreRequest;
import com.example.bilingual.dto.response.*;
import com.example.bilingual.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ResultService {

    private final ResultRepository resultRepository;
    private final QuestionAnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final ClientRepository clientRepository;
    private final JavaMailSender javaMailSender;

    public List<ResultResponse> getAllResults() {
        log.info("Get all results done successfully");
        return resultRepository.getAllResults();
    }

    public SimpleResponse sendResultsToEmail(Long id) throws MessagingException {
        Result result = resultRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Result with id {} not found", id);
                    throw new NotFoundException("Result not found");
                });
        Client client = clientRepository.findById(result.getClient().getId()).orElseThrow(
                () -> {
                    log.error("Client with id {} not found", result.getClient().getId());
                    throw new NotFoundException("Client not found");
                });
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        messageHelper.setSubject("[bilingual]");
        messageHelper.setFrom("mavlyansadirov34@gmail.com");
        messageHelper.setTo(client.getUser().getEmail());
        messageHelper.setText("Your result is " + result.getScore(), true);
        javaMailSender.send(mimeMessage);
        log.info("Result sent to email successfully");
        return new SimpleResponse("Result was successfully sent to email");
    }

    public ViewResultResponse giveResultResponse(Long id) {
        Result result = resultRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Result with id {} not found", id);
                    throw new NotFoundException("Result not found");
                });
        Float score = 0f;
        int status = 0;
        for (QuestionAnswer questionAnswer : result.getQuestionAnswers()) {
            score += questionAnswer.getScore();
            if (questionAnswer.getStatus() == Status.NOT_EVALUATED) {
                status++;
            }
        }
        result.setScore(score);
        if (status == 0) {
            result.setStatus(Status.EVALUATED);
        }
        log.info("Give result response was successfully");
        return new ViewResultResponse(
                result.getId(),
                result.getClient().getFirstName() + " " + result.getClient().getLastName(),
                result.getTest().getTitle(),
                result.getDateOfSubmission(),
                result.getScore(),
                result.getStatus(), answerRepository.getAllQuestionAnswerByResultId(result.getId()));
    }

    public ViewResultResponse giveScoreForQuestion(ScoreRequest scoreRequest) {
        QuestionAnswer answer = answerRepository.findById(scoreRequest.getQuestionId())
                .orElseThrow(() -> {
                    log.error("Question with id {} not found", scoreRequest.getQuestionId());
                    throw new NotFoundException("Question answer not found");
                });
        if (answer.getQuestion().getQuestionType().equals(QuestionType.SELECT_THE_REAL_ENGLISH_WORDS) ||
                answer.getQuestion().getQuestionType().equals(QuestionType.LISTEN_AND_SELECT_ENGLISH_WORDS) ||
                answer.getQuestion().getQuestionType().equals(QuestionType.SELECT_THE_MAIN_IDEA) ||
                answer.getQuestion().getQuestionType().equals(QuestionType.SELECT_THE_BEST_TITLE)) {
            answer.setScore(scoreRequest.getScore());
        } else {
            answer.setScore(scoreRequest.getScore());
        }
        answer.setStatus(Status.EVALUATED);
        answer.setSeen(true);
        log.info("Score given for question done successfully");
        return giveResultResponse(answer.getResult().getId());
    }

    public List<ClientResultResponse> getAllClientResults(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Client client = clientRepository.findClientByUserEmail(user.getEmail()).
                orElseThrow(() -> {
                    log.error("Client with email {} not found", user.getEmail());
                    throw new NotFoundException("Client not found");
                });
        log.info("Get all client results done successfully");
        return resultRepository.getAllClientResults(client.getId());
    }

    public List<ResultResponse> deleteResult(Long id) {
        Result result = resultRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Result with id {} not found", id);
                    throw new NotFoundException("Result not found");
                });
        List<QuestionAnswer> questions = answerRepository.getAllQuestionsByResultId(id);
        for (QuestionAnswer question : questions) {
            result.getQuestionAnswers().remove(question);
            question.setResult(null);
            answerRepository.deleteQuestionAnswerById(question.getId());
        }
        resultRepository.delete(result);
        log.info("Result deleted successfully");
        return getAllResults();
    }

    public List<ClientResultResponse> delete(Long id, Authentication authentication) {
        Result result = resultRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Result with id {} not found", id);
                    throw new NotFoundException("Result not found");
                });
        List<QuestionAnswer> questions = answerRepository.getAllQuestionsByResultId(result.getId());
        for (QuestionAnswer question : questions) {
            result.getQuestionAnswers().remove(question);
            question.setResult(null);
            answerRepository.deleteQuestionAnswerById(question.getId());
        }
        resultRepository.delete(result);
        log.info("Result deleted successfully");
        return getAllClientResults(authentication);
    }

    public ViewResultResponse getResultById(Long id) {
        ViewResultResponse result = resultRepository.getResultById(id).
                orElseThrow(() -> {
                    log.error("Result with id {} not found", id);
                    throw new NotFoundException("Result not found");
                });
        List<QuestionAnswerResponse> questions = answerRepository.getAllQuestionAnswerByResultId(id);
        result.setQuestions(questions);
        log.info("Get result by id done successfully");
        return result;
    }

    public CheckQuestionResponse getAnswer(Long id) {
        CheckQuestionResponse checkResponse = null;
        QuestionAnswer answer = answerRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Answer with id {} not found", id);
                    throw new NotFoundException("Answer not found");
                });
        Question question = questionRepository.findById(answer.getQuestion().getId())
                .orElseThrow(() -> {
                    log.error("Question with id {} not found", answer.getQuestion().getId());
                    throw new NotFoundException("Question not found");
                });
        if (question.getQuestionType().equals(QuestionType.SELECT_THE_REAL_ENGLISH_WORDS) ||
                question.getQuestionType().equals(QuestionType.LISTEN_AND_SELECT_ENGLISH_WORDS)) {
            List<OptionResponse> options = new ArrayList<>();
            for (Option option : question.getOptions()) {
                options.add(new OptionResponse(option));
            }
            List<OptionResponse> userOptions = new ArrayList<>();
            for (Option o : answer.getOptions()) {
                userOptions.add(new OptionResponse(o.getId(), o.getTitle(), o.getOption()));
            }
            checkResponse = new CheckQuestionResponse(options, userOptions);
        } else if (question.getQuestionType().equals(QuestionType.TYPE_WHAT_YOU_HEAR)) {
            checkResponse = new CheckQuestionResponse(
                    question.getCorrectAnswer(),
                    answer.getTextResponseUser(),
                    answer.getCountOfPlays(),
                    question.getNumberOfReplays());
        } else if (question.getQuestionType().equals(QuestionType.DESCRIBE_IMAGE)) {
            checkResponse = new CheckQuestionResponse(question.getCorrectAnswer(), answer.getTextResponseUser());
        } else if (question.getQuestionType().equals(QuestionType.RECORD_SAYING_STATEMENT)) {
            checkResponse = new CheckQuestionResponse(question.getStatement(), answer.getTextResponseUser());
        } else if (question.getQuestionType().equals(QuestionType.RESPOND_IN_AT_LEAST_N_WORDS)) {
            checkResponse = new CheckQuestionResponse(
                    question.getMinWords(),
                    question.getStatement(),
                    answer.getTextResponseUser(),
                    answer.getNumberOfWords());
        } else if (question.getQuestionType().equals(QuestionType.HIGHLIGHT_THE_ANSWER)) {
            checkResponse = new CheckQuestionResponse(
                    question.getPassage(),
                    question.getStatement(),
                    question.getCorrectAnswer(),
                    answer.getTextResponseUser());
        } else if (question.getQuestionType().equals(QuestionType.SELECT_THE_MAIN_IDEA) ||
                question.getQuestionType().equals(QuestionType.SELECT_THE_BEST_TITLE)) {
            List<OptionResponse> options = new ArrayList<>();
            for (Option option : question.getOptions()) {
                options.add(new OptionResponse(option));
            }
            List<OptionResponse> userOptions = new ArrayList<>();
            for (Option o : answer.getOptions()) {
                userOptions.add(new OptionResponse(o.getId(), o.getTitle(), o.getOption()));
            }
            checkResponse = new CheckQuestionResponse(question.getPassage(), options, userOptions);
        }
        assert checkResponse != null;
        checkResponse.setId(answer.getId());
        checkResponse.setUser(answer.getResult().getClient().getFirstName()
                + " " + answer.getResult().getClient().getLastName());
        checkResponse.setTestTitle(answer.getResult().getTest().getTitle());
        checkResponse.setQuestionTitle(answer.getQuestion().getTitle());
        checkResponse.setDuration(answer.getQuestion().getDuration());
        checkResponse.setQuestionType(answer.getQuestion().getQuestionType());
        if (answer.getQuestion().getQuestionType() != QuestionType.DESCRIBE_IMAGE) {
            checkResponse.setScoreOfQuestion(answer.getScore());
        }
        log.info("Get answer done successfully");
        return checkResponse;
    }
}

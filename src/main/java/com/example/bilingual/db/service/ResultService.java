package com.example.bilingual.db.service;

import com.example.bilingual.db.model.Client;
import com.example.bilingual.db.model.QuestionAnswer;
import com.example.bilingual.db.model.Result;
import com.example.bilingual.db.model.User;
import com.example.bilingual.db.model.enums.QuestionType;
import com.example.bilingual.db.model.enums.Status;
import com.example.bilingual.db.repository.ClientRepository;
import com.example.bilingual.db.repository.QuestionAnswerRepository;
import com.example.bilingual.db.repository.ResultRepository;
import com.example.bilingual.dto.request.ScoreRequest;
import com.example.bilingual.dto.response.*;
import com.example.bilingual.exception.NotFoundException;
import com.example.bilingual.security.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultService {

    private final ResultRepository resultRepository;
    private final QuestionAnswerRepository answerRepository;
    private final ClientRepository clientRepository;
    private final JavaMailSender javaMailSender;

    public List<ResultResponse> getAllResults() {
        return resultRepository.getAllResults();
    }

    public SimpleResponse sendResultsToEmail(Long id) throws MessagingException {
        Result result = resultRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Result not found"));
        Client client = clientRepository.findById(result.getClient().getId()).orElseThrow(
                () -> new NotFoundException("Client not found"));
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        messageHelper.setSubject("[bilingual]");
        messageHelper.setFrom("mavlyansadirov34@gmail.com");
        messageHelper.setTo(client.getUser().getEmail());
        messageHelper.setText("Result sent to user's email", true);
        javaMailSender.send(mimeMessage);
        return new SimpleResponse("Result sent to users' email successfully");
    }

    public ViewResultResponse giveResultResponse(Long id) {
        Result result = resultRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Result not found"));
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
                .orElseThrow(() -> new NotFoundException("Question answer not found"));
        if (answer.getQuestion().getQuestionType().equals(QuestionType.SELECT_THE_REAL_ENGLISH_WORDS) ||
                answer.getQuestion().getQuestionType().equals(QuestionType.LISTEN_AND_SELECT_ENGLISH_WORDS) ||
                answer.getQuestion().getQuestionType().equals(QuestionType.SELECT_THE_MAIN_IDEA) ||
                answer.getQuestion().getQuestionType().equals(QuestionType.SELECT_THE_BEST_TITLE)) {
            answer.setScore(answer.getScore());
        } else {
            answer.setScore(scoreRequest.getScore());
        }
        answer.setStatus(Status.EVALUATED);
        answer.setSeen(true);
        return giveResultResponse(answer.getResult().getId());
    }

    public List<ClientResultResponse> getAllClientResults(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Client client = clientRepository.findClientByUserEmail(user.getEmail()).
                orElseThrow(() -> new NotFoundException("Client not found"));
        return resultRepository.getAllClientResults(client.getId());
    }

    public List<ResultResponse> deleteResult(Long id) {
        resultRepository.deleteById(id);
        return getAllResults();
    }

    public List<ClientResultResponse> delete(Long id, Authentication authentication) {
        Result result = resultRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Result not found"));
        resultRepository.delete(result);
        return getAllClientResults(authentication);
    }

    public ViewResultResponse getResultById(Long id) {
        ViewResultResponse result = resultRepository.getResultById(id).
                orElseThrow(() -> new NotFoundException("Result not found"));
        List<QuestionAnswerResponse> questions = answerRepository.getAllQuestionAnswerByResultId(id);
        result.setQuestions(questions);
        return result;
    }
}

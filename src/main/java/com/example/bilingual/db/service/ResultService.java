package com.example.bilingual.db.service;

import com.example.bilingual.db.model.Client;
import com.example.bilingual.db.model.Result;
import com.example.bilingual.db.model.User;
import com.example.bilingual.db.repository.ClientRepository;
import com.example.bilingual.db.repository.QuestionAnswerRepository;
import com.example.bilingual.db.repository.ResultRepository;
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
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    public List<ResultResponse> getAllResults() {
        return resultRepository.getAllResults();
    }

    public SimpleResponse sendResultsToEmail(Long id) throws MessagingException {
        Client client = clientRepository.findById(id).orElseThrow(
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

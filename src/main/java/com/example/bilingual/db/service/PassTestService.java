package com.example.bilingual.db.service;

import com.example.bilingual.db.model.Client;
import com.example.bilingual.db.model.Result;
import com.example.bilingual.db.repository.*;
import com.example.bilingual.dto.request.PassTestRequest;
import com.example.bilingual.dto.response.SimpleResponse;
import com.example.bilingual.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

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
        Client client = clientRepository.findClientByUserEmail(principal.getName())
                .orElseThrow(() -> new NotFoundException("Client not found"));


        return null;
    }
}

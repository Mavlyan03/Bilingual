package com.example.bilingual.db.service;

import com.example.bilingual.db.repository.ResultRepository;
import com.example.bilingual.db.repository.UserRepository;
import com.example.bilingual.dto.response.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultService {

    private final ResultRepository resultRepository;
    private final UserRepository userRepository;

    public List<ResultResponse> getAllResults() {
        return resultRepository.getAllResults();
    }


}

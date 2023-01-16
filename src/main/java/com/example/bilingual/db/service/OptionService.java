package com.example.bilingual.db.service;

import com.example.bilingual.db.model.Option;
import com.example.bilingual.db.repository.OptionRepository;
import com.example.bilingual.dto.response.SimpleResponse;
import com.example.bilingual.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OptionService {

    private final OptionRepository optionRepository;

    public SimpleResponse deleteOption(Long id) {
        Option option = optionRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Option not found"));
        optionRepository.delete(option);
        return new SimpleResponse(String.format("Option with %s id deleted", id));
    }
}

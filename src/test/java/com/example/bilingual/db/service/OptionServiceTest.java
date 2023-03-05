package com.example.bilingual.db.service;

import com.example.bilingual.db.repository.OptionRepository;
import com.example.bilingual.dto.response.SimpleResponse;
import com.example.bilingual.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OptionServiceTest {

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private OptionService optionService;

    @Test
    void deleteOption() {
        SimpleResponse simpleResponse = optionService.deleteOption(1L);

        assertNotNull(simpleResponse);
        assertThatThrownBy(() -> optionRepository.findById(1L).orElseThrow(
                () -> new NotFoundException("Option not found")))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Option not found");
    }
}
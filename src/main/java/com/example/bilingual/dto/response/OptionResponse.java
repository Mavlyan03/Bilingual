package com.example.bilingual.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OptionResponse {
    private Long id;
    private String title;
    private String option;
    private Boolean isTrue;
}

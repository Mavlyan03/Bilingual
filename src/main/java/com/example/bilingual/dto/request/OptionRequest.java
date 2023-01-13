package com.example.bilingual.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptionRequest {
    private String title;
    private String option;
    private Boolean isTrue;
}

package com.example.bilingual.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestionAnswerRequest {
    private Long questionId;
    private List<Long> options;
    private Integer numberOfReplays;
    private String answer;
}

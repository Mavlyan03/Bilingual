package com.example.bilingual.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PassTestRequest {
    private Long testId;
    private List<QuestionAnswerRequest> questions;
}

package com.example.bilingual.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScoreRequest {
    private Long questionId;
    private Float score;
}

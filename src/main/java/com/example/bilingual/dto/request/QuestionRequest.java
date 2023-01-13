package com.example.bilingual.dto.request;

import com.example.bilingual.db.model.enums.QuestionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionRequest {
    private Long testId;
    private String title;
    private Integer duration;
    private QuestionType questionType;
}

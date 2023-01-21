package com.example.bilingual.dto.request;

import com.example.bilingual.db.model.enums.QuestionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateQuestionRequest {
    private Long id;
    private String title;
    private Integer duration;
    private QuestionType questionType;
}

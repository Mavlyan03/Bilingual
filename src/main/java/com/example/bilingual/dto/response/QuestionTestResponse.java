package com.example.bilingual.dto.response;

import com.example.bilingual.db.model.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QuestionTestResponse {
    private Long id;
    private String name;
    private Integer duration;
    private QuestionType questionType;
    private Boolean isActive;
}

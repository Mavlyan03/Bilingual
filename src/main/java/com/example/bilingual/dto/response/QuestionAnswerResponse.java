package com.example.bilingual.dto.response;

import com.example.bilingual.db.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QuestionAnswerResponse {
    private Long id;
    private String question;
    private Float score;
    private Status status;
    private Boolean seen;
}

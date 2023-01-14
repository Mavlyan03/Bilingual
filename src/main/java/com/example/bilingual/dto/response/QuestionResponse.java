package com.example.bilingual.dto.response;

import com.example.bilingual.db.model.enums.QuestionType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestionResponse {
    private Long id;
    private String title;
    private String duration;
    private String shortDescription;
    private QuestionType questionType;
    private Integer numberOfReplays;
    private String correctAnswer;
    private Integer numberOfWords;
    private String statement;
    private String passage;
    private List<OptionResponse> optionResponses;
    private String content;
    private Boolean isTrue;
    private String questionNumber;
}

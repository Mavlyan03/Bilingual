package com.example.bilingual.dto.response;

import com.example.bilingual.db.model.enums.QuestionType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CheckQuestionResponse {
    private Long id;

    private String user;

    private String testTitle;

    private String questionTitle;

    private Integer duration;

    private QuestionType questionType;

    private Float scoreOfQuestion;

    private List<OptionResponse> options;

    private Integer minNumberOfReplays;

    private String link;

    private String correctAnswer;

    private String statement;

    private Integer minNumberOfWords;

    private String passage;

    private List<OptionResponse> userOptionsAnswer;

    private String userAnswer;

    private Integer userNumberOfPlays;

}

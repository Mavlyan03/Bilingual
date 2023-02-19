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

    public CheckQuestionResponse(List<OptionResponse> options, List<OptionResponse> userOptions) {
        this.options = options;
        this.userOptionsAnswer = userOptions;
    }

    public CheckQuestionResponse(String correctAnswer, String userAnswer, Integer numberOfPlays, Integer minNumberOfReplays) {
        this.correctAnswer = correctAnswer;
        this.userAnswer = userAnswer;
        this.userNumberOfPlays = numberOfPlays;
        this.minNumberOfReplays = minNumberOfReplays;
    }

    public CheckQuestionResponse(String correctAnswer, String userAnswer) {
        this.correctAnswer = correctAnswer;
        this.userAnswer = userAnswer;
    }

    public CheckQuestionResponse(Integer minNumberOfWords, String statement, String userAnswer, Integer numberOfWords) {
        this.minNumberOfWords = minNumberOfWords;
        this.correctAnswer = statement;
        this.userAnswer = userAnswer;
        this.userNumberOfPlays = numberOfWords;
    }

    public CheckQuestionResponse(String passage, String statement, String correctAnswer, String userAnswer) {
        this.passage = passage;
        this.statement = statement;
        this.correctAnswer = correctAnswer;
        this.userAnswer = userAnswer;
    }

    public CheckQuestionResponse(String passage, List<OptionResponse> options, List<OptionResponse> userOptions) {
        this.passage = passage;
        this.options = options;
        this.userOptionsAnswer = userOptions;
    }
}

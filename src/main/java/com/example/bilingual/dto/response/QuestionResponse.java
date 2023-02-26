package com.example.bilingual.dto.response;

import com.example.bilingual.db.model.Question;
import com.example.bilingual.db.model.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestionResponse {
    private Long id;
    private String title;
    private Integer duration;
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

    public QuestionResponse(Question question) {
        this.id = question.getId();
        this.title = question.getTitle();
        this.duration = question.getDuration();
        this.shortDescription = question.getTest().getDescription();
        this.questionType = question.getQuestionType();
        this.numberOfReplays = question.getNumberOfReplays();
        this.correctAnswer = question.getCorrectAnswer();
        this.numberOfWords = question.getMinWords();
        this.statement = question.getStatement();
        this.passage = question.getPassage();
        this.content = question.getContent().getContent();
        this.isTrue = question.getIsActive();
    }
}

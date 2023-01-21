package com.example.bilingual.dto.request;

import com.example.bilingual.db.model.enums.QuestionType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateQuestionRequest {
    private Long id;
    private String title;

    private String statement;

    private String passage;

    private Integer numberOfReplays;

    private Integer duration;

    private Integer minNumberOfWords;

    private String correctAnswer;

    private String content;

    private List<Long> willDelete;

    private List<Long> willUpdate;

    private List<OptionRequest> optionRequests;
}

package com.example.bilingual.dto.request;

import com.example.bilingual.db.model.enums.QuestionType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
public class QuestionRequest {
    private Long testId;
    @NotBlank
    private String title;
    private Integer duration;
    private QuestionType questionType;
    private List<OptionRequest> options;
    private Integer numberOfReplays;
    private String correctAnswer;
    private Integer numberOfWords;
    private String passage;
    private String statement;
    private ContentRequest contentRequest;
}

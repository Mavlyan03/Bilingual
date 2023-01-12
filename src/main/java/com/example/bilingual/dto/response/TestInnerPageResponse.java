package com.example.bilingual.dto.response;

import com.example.bilingual.db.model.Test;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TestInnerPageResponse {
    private Long id;
    private String title;
    private String description;
    private Integer duration;
    private List<QuestionTestResponse> questionTestResponses;

    public TestInnerPageResponse(Test test) {
        this.id = test.getId();
        this.title = test.getTitle();
        this.description = test.getDescription();
        this.duration = test.getQuestions().size();
    }
}

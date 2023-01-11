package com.example.bilingual.dto.response;

import com.example.bilingual.db.model.Test;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestResponse {
    private Long id;
    private String title;
    private String shortDescription;
    private Boolean isActive;

    public TestResponse(Test test) {
        this.id = test.getId();
        this.title = test.getTitle();
        this.shortDescription = test.getDescription();
        this.isActive = test.getIsActive();
    }
}

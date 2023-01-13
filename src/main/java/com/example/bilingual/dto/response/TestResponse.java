package com.example.bilingual.dto.response;

import com.example.bilingual.db.model.Test;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestResponse {
    private Long id;
    private String title;
    private String shortDescription;
    private Boolean isActive;

    public TestResponse(Long id, String title, String shortDescription, Boolean isActive) {
        this.id = id;
        this.title = title;
        this.shortDescription = shortDescription;
        this.isActive = isActive;
    }

    public TestResponse(Test test) {
        this.id = test.getId();
        this.title = test.getTitle();
        this.shortDescription = test.getDescription();
        this.isActive = true;
    }
}

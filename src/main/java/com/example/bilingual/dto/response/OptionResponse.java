package com.example.bilingual.dto.response;

import com.example.bilingual.db.model.Option;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OptionResponse {
    private Long id;
    private String title;
    private String option;
    private Boolean isTrue;

    public OptionResponse(Option o) {
        this.id = o.getId();
        this.title = o.getTitle();
        this.option = o.getOption();
        this.isTrue = o.getIsTrue();
    }

    public OptionResponse(Long id, String title, String option) {
        this.id = id;
        this.title = title;
        this.option = option;
    }
}

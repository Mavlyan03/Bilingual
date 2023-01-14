package com.example.bilingual.dto.request;

import com.example.bilingual.db.model.enums.ContentFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContentRequest {
    private String content;
    private ContentFormat contentFormat;
}

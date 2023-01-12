package com.example.bilingual.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NotBlank
public class TestRequest {
    private String title;
    private String shortDescription;
}

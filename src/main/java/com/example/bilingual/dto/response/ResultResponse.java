package com.example.bilingual.dto.response;

import com.example.bilingual.db.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ResultResponse {
    private Long id;
    private String clientName;
    private LocalDateTime dateOfSubmission;
    private String testName;
    private Status status;
    private Float score;
}

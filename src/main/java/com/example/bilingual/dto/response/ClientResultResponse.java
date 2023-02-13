package com.example.bilingual.dto.response;

import com.example.bilingual.db.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ClientResultResponse {
    private Long id;
    private String testName;
    private LocalDateTime dateOfSubmission;
    private Float score;
    private Status status;
}

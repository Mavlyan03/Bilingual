package com.example.bilingual.dto.response;

import com.example.bilingual.db.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ViewResultResponse {
    private Long id;
    private String user;
    private String test;
    private LocalDateTime dateOfSubmission;
    private Float finalScore;
    private Status finalStatus;
    private List<QuestionAnswerResponse> questions;

    public ViewResultResponse(Long id, String user, String test, LocalDateTime dateOfSubmission, Float finalScore, Status finalStatus) {
        this.id = id;
        this.user = user;
        this.test = test;
        this.dateOfSubmission = dateOfSubmission;
        this.finalScore = finalScore;
        this.finalStatus = finalStatus;
    }
}

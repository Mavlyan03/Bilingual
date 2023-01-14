package com.example.bilingual.api;

import com.example.bilingual.db.service.QuestionService;
import com.example.bilingual.dto.request.QuestionRequest;
import com.example.bilingual.dto.response.SimpleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/question")
@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Question API", description = "ADMIN question endpoints")
public class QuestionApi {

    private final QuestionService questionService;

    @PostMapping
    @Operation(summary = "Save question",
            description = "ADMIN can save a new question")
    public SimpleResponse saveQuestion(@RequestBody QuestionRequest questionRequest) {
        return questionService.saveQuestion(questionRequest);
    }
}

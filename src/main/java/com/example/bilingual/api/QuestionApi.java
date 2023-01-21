package com.example.bilingual.api;

import com.example.bilingual.db.service.QuestionService;
import com.example.bilingual.dto.request.QuestionRequest;
import com.example.bilingual.dto.request.UpdateQuestionRequest;
import com.example.bilingual.dto.response.QuestionResponse;
import com.example.bilingual.dto.response.QuestionTestResponse;
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

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete question",
            description = "ADMIN can delete question by id")
    public SimpleResponse deleteQuestion(@PathVariable Long id) {
        return questionService.deleteQuestion(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get question by id",
            description = "ADMIN can get question by id")
    public QuestionResponse getQuestionById(@PathVariable Long id) {
        return questionService.getById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Enable - Disable",
            description = "ADMIN can change status of question")
    public SimpleResponse enableDisable(@PathVariable Long id) {
        return questionService.enableDisable(id);
    }

    @PutMapping
    @Operation(summary = "Update question",
            description = "ADMIN can update question by id")
    public SimpleResponse updateQuestion(@RequestBody UpdateQuestionRequest questionRequest) {
        return questionService.updateQuestion(questionRequest);
    }
}

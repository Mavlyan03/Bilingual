package com.example.bilingual.api;

import com.example.bilingual.db.service.ResultService;
import com.example.bilingual.dto.request.ScoreRequest;
import com.example.bilingual.dto.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/result")
@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Result API", description = "ADMIN result endpoints")
public class ResultApi {

    private final ResultService resultService;

    @GetMapping
    @Operation(summary = "Get all results",
            description = "ADMIN can get all results")
    public List<ResultResponse> getAllResults() {
        return resultService.getAllResults();
    }

    @GetMapping("/email/{id}")
    @Operation(summary = "Send result to user's email",
            description = "ADMIN can send result to user's email")
    public SimpleResponse sendResultToEmail(@PathVariable Long id) throws MessagingException {
        return resultService.sendResultsToEmail(id);
    }

    @PutMapping("/score")
    @Operation(summary = "Give a score to the question",
            description = "ADMIN can give a score to the question")
    public ViewResultResponse giveScoreToQuestion(@RequestBody ScoreRequest scoreRequest) {
        return resultService.giveScoreForQuestion(scoreRequest);
    }

    @DeleteMapping("/result/{id}")
    @Operation(summary = "Delete result",
            description = "ADMIN can delete result by id")
    public List<ResultResponse> deleteResult(@PathVariable Long id) {
        return resultService.deleteResult(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete clients result",
            description = "CLIENT can delete own results")
    @PreAuthorize("hasAuthority('CLIENT')")
    public List<ClientResultResponse> delete(@PathVariable Long id,
                                             Authentication authentication) {
        return resultService.delete(id, authentication);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get result by id",
            description = "ADMIN can get result by id")
    public ViewResultResponse getResultById(@PathVariable Long id) {
        return resultService.getResultById(id);
    }

    @GetMapping("/answer/{id}")
    @Operation(summary = "Answer response",
            description = "ADMIN can get client answer response for evaluation")
    public CheckQuestionResponse getAnswer(@PathVariable Long id) {
        return resultService.getAnswer(id);
    }
}

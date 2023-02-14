package com.example.bilingual.api;

import com.example.bilingual.db.service.ResultService;
import com.example.bilingual.dto.response.ResultResponse;
import com.example.bilingual.dto.response.SimpleResponse;
import com.example.bilingual.dto.response.ViewResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete result",
            description = "ADMIN can delete result by id")
    @PreAuthorize("hasAnyAuthority('CLIENT','ADMIN')")
    public SimpleResponse deleteResult(@PathVariable Long id) {
        return resultService.deleteResult(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get result by id",
            description = "ADMIN can get result by id")
    public ViewResultResponse getResultById(@PathVariable Long id) {
        return resultService.getResultById(id);
    }
}

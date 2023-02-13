package com.example.bilingual.api;

import com.example.bilingual.db.service.ResultService;
import com.example.bilingual.dto.response.ResultResponse;
import com.example.bilingual.dto.response.SimpleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete result",
            description = "ADMIN can delete result by id")
    public SimpleResponse deleteResult(@PathVariable Long id) {
        return resultService.deleteResult(id);
    }
}

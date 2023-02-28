package com.example.bilingual.api;

import com.example.bilingual.db.service.TestService;
import com.example.bilingual.dto.request.TestRequest;
import com.example.bilingual.dto.response.SimpleResponse;
import com.example.bilingual.dto.response.TestInnerPageResponse;
import com.example.bilingual.dto.response.TestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/test")
@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin(origins = "*",maxAge = 3600)
@Tag(name = "Test API",description = "ADMIN test endpoints")
public class TestApi {

    private final TestService testService;

    @PostMapping
    @Operation(summary = "Save test",
            description = "ADMIN can save a new test")
    public TestResponse saveTest(@RequestBody TestRequest testRequest) {
        return testService.saveTest(testRequest);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update test",
            description = "ADMIN can update test by id")
    public TestResponse updateTest(@PathVariable Long id, @RequestBody TestRequest testRequest) {
        return testService.updateTest(id, testRequest);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete test",
            description = "ADMIN can delete test by id")
    public SimpleResponse deleteTest(@PathVariable Long id) {
        return testService.deleteTest(id);
    }

    @GetMapping
    @Operation(summary = "Get all tests",
            description = "ADMIN can get all tests")
    public List<TestResponse> getAllTests() {
        return testService.getAllTests();
    }

    @PutMapping("/enable-disable/{id}")
    @Operation(summary = "Enable - Disable Test",
            description = "ADMIN can change status of test")
    public SimpleResponse enableDisable(@PathVariable Long id) {
        return testService.enableDisable(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Test inner page",
            description = "ADMIN can get test inner page")
    public TestInnerPageResponse getTestById(@PathVariable Long id) {
        return testService.getTestById(id);
    }
}

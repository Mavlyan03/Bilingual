package com.example.bilingual.api;

import com.example.bilingual.db.service.PassTestService;
import com.example.bilingual.db.service.TestService;
import com.example.bilingual.dto.request.PassTestRequest;
import com.example.bilingual.dto.response.SimpleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("client/test/api")
@PreAuthorize("hasAuthority('CLIENT')")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Client API", description = "CLIENT pass test endpoints")
public class ClientApi {

    private final PassTestService passTestService;

    @PostMapping
    @Operation(summary = "Pass test",
            description = "CLIENT can pass test")
    private SimpleResponse passTest(@RequestBody PassTestRequest passTestRequest,
                                    Authentication authentication) {
        return passTestService.passTest(passTestRequest, authentication);
    }
}

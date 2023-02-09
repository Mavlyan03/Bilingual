package com.example.bilingual.api;

import com.example.bilingual.db.service.PassTestService;
import com.example.bilingual.db.service.TestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("client/test/api")
@PreAuthorize("hasAuthority('CLIENT')")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Client API", description = "CLIENT pass test endpoints")
public class ClientApi {

    private final PassTestService passTestService;
    private final TestService testService;
}

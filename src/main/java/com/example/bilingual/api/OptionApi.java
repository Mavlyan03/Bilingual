package com.example.bilingual.api;

import com.example.bilingual.db.service.OptionService;
import com.example.bilingual.dto.response.SimpleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("option/api")
@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Option API", description = "ADMIn option endpoints")
public class OptionApi {

    private final OptionService optionService;

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete option",
            description = "ADMIN can delete option by id")
    public SimpleResponse deleteOption(@PathVariable Long id) {
        return optionService.deleteOption(id);
    }
}

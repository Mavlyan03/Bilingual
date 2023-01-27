package com.example.bilingual.dto.request;

import com.example.bilingual.validation.PasswordValid;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NotBlank
public class ForgotPasswordRequest {
    private Long id;
    @PasswordValid
    private String password;
}

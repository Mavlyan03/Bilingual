package com.example.bilingual.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NotBlank
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    @NotBlank
    private String password;
}

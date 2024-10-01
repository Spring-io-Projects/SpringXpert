package com.app.springxpert.iam.infrastructure.adapter.input.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ResetPasswordRequest(
        @Email
        @NotBlank String email,

        @NotBlank String code,

        @NotBlank String newPassword,

        @NotBlank String confirmPassword
) {
}
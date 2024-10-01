package com.app.springxpert.iam.infrastructure.adapter.input.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordRequest(
        @NotBlank String currentPassword,

        @NotBlank String newPassword,

        @NotBlank String confirmPassword
) {
}
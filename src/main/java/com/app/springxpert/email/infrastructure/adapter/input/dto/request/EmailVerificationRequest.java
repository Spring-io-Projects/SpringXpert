package com.app.springxpert.email.infrastructure.adapter.input.dto.request;

import jakarta.validation.constraints.NotBlank;

public record EmailVerificationRequest(
        @NotBlank String to,
        @NotBlank String subject,
        @NotBlank String verificationCode
) {
}
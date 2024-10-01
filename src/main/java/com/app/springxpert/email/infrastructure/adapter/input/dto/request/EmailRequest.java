package com.app.springxpert.email.infrastructure.adapter.input.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record EmailRequest(
        @NotEmpty String[] toUser,
        @NotBlank String subject,
        @NotBlank String message
) {
}
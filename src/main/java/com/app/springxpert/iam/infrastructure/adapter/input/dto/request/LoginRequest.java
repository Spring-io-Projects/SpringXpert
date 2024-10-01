package com.app.springxpert.iam.infrastructure.adapter.input.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank String username,

        @NotBlank String password
) {
}
package com.app.springxpert.shared.infrastructure.dto;

import jakarta.validation.constraints.NotBlank;

public record ResponseDTO(
        @NotBlank String message
) {
}

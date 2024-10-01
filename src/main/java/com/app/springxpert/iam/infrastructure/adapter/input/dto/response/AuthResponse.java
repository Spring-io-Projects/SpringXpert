package com.app.springxpert.iam.infrastructure.adapter.input.dto.response;

public record AuthResponse(
        String username,
        String message,
        Boolean status,
        String jwt
) {
}

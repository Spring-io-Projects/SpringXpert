package com.app.springxpert.email.infrastructure.adapter.input.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record EmailFileRequest(
        @NotEmpty String[] toUser,

        @NotBlank String subject,

        @NotBlank String message,

        @NotNull MultipartFile file
) {
}
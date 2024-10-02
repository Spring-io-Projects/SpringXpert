package com.app.springxpert.iam.infrastructure.adapter.input.dto.request;

import com.app.springxpert.iam.domain.model.aggregate.UserEntity;
import com.app.springxpert.shared.domain.validator.ExistsField;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ResetPasswordRequest(
        @ExistsField(entityClass = UserEntity.class, fieldName = "email")
        @Email
        @NotBlank String email,

        @NotBlank String code,

        @NotBlank String newPassword,

        @NotBlank String confirmPassword
) {
}
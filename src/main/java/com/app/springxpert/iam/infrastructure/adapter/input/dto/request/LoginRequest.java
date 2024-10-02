package com.app.springxpert.iam.infrastructure.adapter.input.dto.request;

import com.app.springxpert.iam.domain.model.aggregate.UserEntity;
import com.app.springxpert.shared.domain.validator.ExistsField;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @ExistsField(entityClass = UserEntity.class, fieldName = "username")
        @NotBlank String username,

        @ExistsField(entityClass = UserEntity.class, fieldName = "password")
        @NotBlank String password
) {
}
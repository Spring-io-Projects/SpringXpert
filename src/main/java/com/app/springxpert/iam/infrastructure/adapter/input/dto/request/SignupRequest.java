package com.app.springxpert.iam.infrastructure.adapter.input.dto.request;

import com.app.springxpert.iam.domain.model.aggregate.UserEntity;
import com.app.springxpert.iam.domain.model.valueobject.RoleEnum;
import com.app.springxpert.shared.domain.validator.UniqueField;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record SignupRequest(
        @UniqueField(entityClass = UserEntity.class, fieldName = "email")
        @Email
        @NotBlank String email,

        @UniqueField(entityClass = UserEntity.class, fieldName = "username")
        @NotBlank String username,

        @NotBlank String password,

        @NotEmpty List<RoleEnum> roleList
) {
}
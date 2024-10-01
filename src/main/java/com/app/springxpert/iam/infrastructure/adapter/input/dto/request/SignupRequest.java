package com.app.springxpert.iam.infrastructure.adapter.input.dto.request;

import com.app.springxpert.iam.domain.model.valueobject.RoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record SignupRequest(
        @Email
        @NotBlank String email,

        @NotBlank String username,

        @NotBlank String password,

        @NotEmpty List<RoleEnum> roleList
) {
}
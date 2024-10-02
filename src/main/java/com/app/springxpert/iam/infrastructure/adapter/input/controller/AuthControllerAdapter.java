package com.app.springxpert.iam.infrastructure.adapter.input.controller;

import com.app.springxpert.iam.application.port.input.service.IAuthServicePort;
import com.app.springxpert.iam.infrastructure.adapter.input.dto.request.LoginRequest;
import com.app.springxpert.iam.infrastructure.adapter.input.dto.request.SignupRequest;
import com.app.springxpert.iam.infrastructure.adapter.input.dto.response.AuthResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/api/v1/auth"}, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication", description = "Identity and Access Management")
public class AuthControllerAdapter {

    private final IAuthServicePort authServicePort;

    @Transactional
    @PostMapping({"/sign-up"})
    @Operation(
            summary = "Sign up a new user",
            description = "Sign up a new user by providing the user's details"
    )
    public ResponseEntity<AuthResponse> signUp(@RequestBody @Valid SignupRequest signupRequest) {
        AuthResponse authResponse = authServicePort.signUp(signupRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }

    @Transactional
    @PostMapping({"/log-in"})
    @Operation(
            summary = "Log in a user",
            description = "Log in a user by providing the user's credentials"
    )
    public ResponseEntity<AuthResponse> logIn(@RequestBody @Valid LoginRequest loginRequest) {
        AuthResponse authResponse = authServicePort.logIn(loginRequest);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(authResponse);
    }
}

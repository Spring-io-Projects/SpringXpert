package com.app.springxpert.iam.application.port.input.service;

import com.app.springxpert.iam.infrastructure.adapter.input.dto.request.LoginRequest;
import com.app.springxpert.iam.infrastructure.adapter.input.dto.request.SignupRequest;
import com.app.springxpert.iam.infrastructure.adapter.input.dto.response.AuthResponse;

public interface IAuthServicePort {
    AuthResponse signUp(SignupRequest signupRequest);
    AuthResponse logIn(LoginRequest loginRequest);
}
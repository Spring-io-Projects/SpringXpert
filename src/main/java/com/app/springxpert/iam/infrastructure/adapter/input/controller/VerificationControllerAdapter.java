package com.app.springxpert.iam.infrastructure.adapter.input.controller;

import com.app.springxpert.iam.application.port.input.service.IVerificationServicePort;
import com.app.springxpert.iam.infrastructure.adapter.input.dto.request.RecoverPasswordRequest;
import com.app.springxpert.iam.infrastructure.adapter.input.dto.request.ResetPasswordRequest;
import com.app.springxpert.iam.infrastructure.adapter.input.dto.request.VerificationCodeRequest;
import com.app.springxpert.shared.infrastructure.dto.ResponseDTO;
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
@RequestMapping(value = {"/api/v1/verification"}, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Verification Management", description = "Endpoints for managing password recovery and verification")
public class VerificationControllerAdapter {

    private final IVerificationServicePort verificationServicePort;

    @Transactional
    @PostMapping({"/recover/password"})
    @Operation(
            summary = "Send recovery code",
            description = "Send a verification code to the user's email for password recovery"
    )
    public ResponseEntity<ResponseDTO> recoverPassword(@RequestBody @Valid RecoverPasswordRequest recoverPasswordRequest) {
        verificationServicePort.recoverPassword(recoverPasswordRequest);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Recovery password sent successfully"));
    }

    @Transactional
    @PostMapping({"/recover/verify-code"})
    @Operation(
            summary = "Verify recovery code",
            description = "Verify the recovery code sent to the user's email"
    )
    public ResponseEntity<ResponseDTO> verifyCode(@RequestBody @Valid VerificationCodeRequest verificationCodeRequest) {
        verificationServicePort.verifyCode(verificationCodeRequest);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Code verified successfully"));
    }

    @Transactional
    @PostMapping({"/recover/reset-password"})
    @Operation(
            summary = "Reset user password",
            description = "Reset the password for the currently logged-in user"
    )
    public ResponseEntity<ResponseDTO> resetPassword(@RequestBody @Valid ResetPasswordRequest resetPasswordRequest) {
        verificationServicePort.resetPassword(resetPasswordRequest);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Password reset successfully"));
    }
}

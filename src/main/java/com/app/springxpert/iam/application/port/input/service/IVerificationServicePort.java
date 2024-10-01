package com.app.springxpert.iam.application.port.input.service;

import com.app.springxpert.iam.infrastructure.adapter.input.dto.request.RecoverPasswordRequest;
import com.app.springxpert.iam.infrastructure.adapter.input.dto.request.ResetPasswordRequest;
import com.app.springxpert.iam.infrastructure.adapter.input.dto.request.VerificationCodeRequest;

public interface IVerificationServicePort {
    void recoverPassword(RecoverPasswordRequest recoverPasswordRequest);
    void verifyCode(VerificationCodeRequest verificationCodeRequest);
    void resetPassword(ResetPasswordRequest resetPasswordRequest);
}

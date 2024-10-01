package com.app.springxpert.iam.application.usecase;

import com.app.springxpert.email.application.port.output.persistence.IEmailPersistencePort;
import com.app.springxpert.email.infrastructure.adapter.input.dto.request.EmailVerificationRequest;
import com.app.springxpert.iam.application.port.input.service.IUserServicePort;
import com.app.springxpert.iam.application.port.input.service.IVerificationServicePort;
import com.app.springxpert.iam.application.port.output.persistence.IUserPersistencePort;
import com.app.springxpert.iam.application.port.output.persistence.IVerificationPersistencePort;
import com.app.springxpert.iam.domain.model.aggregate.UserEntity;
import com.app.springxpert.iam.domain.model.entity.VerificationEntity;
import com.app.springxpert.iam.domain.model.valueobject.CodeEnum;
import com.app.springxpert.iam.infrastructure.adapter.input.dto.request.RecoverPasswordRequest;
import com.app.springxpert.iam.infrastructure.adapter.input.dto.request.ResetPasswordRequest;
import com.app.springxpert.iam.infrastructure.adapter.input.dto.request.VerificationCodeRequest;
import com.app.springxpert.shared.application.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class VerificationServiceImpl implements IVerificationServicePort {

    @Value("${verification.code.expiration}")
    private int verificationCodeExpiration;

    private final IUserServicePort userServicePort;
    private final IVerificationPersistencePort verificationPersistencePort;
    private final IUserPersistencePort userPersistencePort;
    private final IEmailPersistencePort emailPersistencePort;
    private final PasswordEncoder passwordEncoder;
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
    private final Random random = new Random();

    @Override
    public void recoverPassword(RecoverPasswordRequest recoverPasswordRequest) {
        Optional<UserEntity> userEntity = userPersistencePort.findUserEntityByEmail(recoverPasswordRequest.email());
        if (userEntity.isPresent()) {
            LocalDateTime verificationCodeExpiresAt = generateVerificationCodeExpiresAt();
            String verificationCode = generateVerificationCode();
            String htmlMessage = generateMessage(verificationCode);

            VerificationEntity verificationEntity = new VerificationEntity();
            verificationEntity.setCode(verificationCode);
            verificationEntity.setExpiredAt(verificationCodeExpiresAt);
            verificationEntity.setUser(userEntity.get());

            verificationPersistencePort.saveVerification(verificationEntity);
            scheduleExpiry(verificationEntity, verificationCodeExpiresAt);

            emailPersistencePort.sendVerificationEmail(
                    new EmailVerificationRequest(recoverPasswordRequest.email(), "Account Verification", htmlMessage)
            );
        }
        else {
            throw new ResourceNotFoundException("User not found");
        }
    }

    @Override
    public void verifyCode(VerificationCodeRequest verificationCodeRequest) {
        VerificationEntity verification = checkVerificationEntity(verificationCodeRequest.code(), verificationCodeRequest.email());
        if (verification.getStatus().equals(CodeEnum.PENDING)) {
            if (!codesMatch(verificationCodeRequest.code(), verification.getCode())) {
                throw new IllegalArgumentException("Incorrect verification code");
            }
            else {
                verification.setStatus(CodeEnum.VERIFIED);
                verificationPersistencePort.saveVerification(verification);
            }
        }
        else if (verification.getStatus().equals(CodeEnum.EXPIRED)) {
            throw new IllegalArgumentException("Code has expired");
        }
        else {
            throw new IllegalArgumentException("Code has already been verified");
        }
    }

    @Override
    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        VerificationEntity verificationEntity = checkVerificationEntity(resetPasswordRequest.code(), resetPasswordRequest.email());
        log.info("Verification entity: {}", verificationEntity);
        if (verificationEntity.getStatus().equals(CodeEnum.VERIFIED)) {
            userServicePort.checkPassword(resetPasswordRequest.newPassword());
            if (!passwordsMatch(resetPasswordRequest.newPassword(), resetPasswordRequest.confirmPassword())) {
                throw new IllegalArgumentException("Passwords do not match");
            }
            else {
                String newPassword = passwordEncoder.encode(resetPasswordRequest.confirmPassword());
                userPersistencePort.changePassword(newPassword, verificationEntity.getUser().getId());
            }
        }
        else {
            throw new ResourceNotFoundException("Code has not been verified");
        }
    }

    private String generateMessage(String verificationCode) {
        return "<html>" +
                    "<body style=font-family: Arial, sans-serif;>" +
                        "<div style=background-color: #f5f5f5; padding: 20px;>" +
                            "<h2 style=color: #333;>Recover password request!</h2>" +
                            "<p style=font-size: 16px;>Please enter the verification code below to continue:</p>" +
                            "<div style=background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);>" +
                                "<h3 style=color: #333;>Verification Code:</h3>" +
                                "<p style=font-size: 18px; font-weight: bold; color: #007bff;>" + verificationCode + "</p>" +
                            "</div>" +
                        "</div>" +
                    "</body>" +
                "</html>";
    }
    private void scheduleExpiry(VerificationEntity verificationEntity, LocalDateTime expiryTime) {
        long delay = Duration.between(LocalDateTime.now(), expiryTime).toMillis();
        scheduledExecutorService.schedule(() -> {
            verificationEntity.setStatus(CodeEnum.EXPIRED);
            verificationPersistencePort.saveVerification(verificationEntity);
        }, delay, TimeUnit.MILLISECONDS);
    }
    private String generateVerificationCode() {
        return String.format("%06d", random.nextInt(1000000));
    }
    private LocalDateTime generateVerificationCodeExpiresAt() {
        return LocalDateTime.now().plusMinutes(verificationCodeExpiration);
    }
    private boolean passwordsMatch(String newPassword, String confirmPassword) {
        return newPassword.equals(confirmPassword);
    }
    private boolean codesMatch(String codeEntered, String codeCreated) {
        return codeEntered.equals(codeCreated);
    }
    private VerificationEntity checkVerificationEntity(String code, String email) {
        Optional<VerificationEntity> verificationEntity = verificationPersistencePort.findVerificationEntityByCodeAndEmail(code, email);
        if (verificationEntity.isPresent()) {
            return verificationEntity.get();
        }
        else {
            throw new ResourceNotFoundException("Code has not been verified");
        }
    }
}
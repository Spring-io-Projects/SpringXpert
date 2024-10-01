package com.app.springxpert.email.application.port.output.persistence;

import com.app.springxpert.email.infrastructure.adapter.input.dto.request.EmailFileRequest;
import com.app.springxpert.email.infrastructure.adapter.input.dto.request.EmailRequest;
import com.app.springxpert.email.infrastructure.adapter.input.dto.request.EmailVerificationRequest;
import java.io.File;

public interface IEmailPersistencePort {
    void sendEmail(EmailRequest emailRequest);
    void sendEmailWithFile(EmailFileRequest emailFileRequest, File file);
    void sendVerificationEmail(EmailVerificationRequest emailVerificationRequest);
}
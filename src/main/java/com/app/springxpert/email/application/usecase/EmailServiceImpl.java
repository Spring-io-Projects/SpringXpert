package com.app.springxpert.email.application.usecase;

import com.app.springxpert.email.application.port.input.service.IEmailServicePort;
import com.app.springxpert.email.application.port.output.persistence.IEmailPersistencePort;
import com.app.springxpert.email.infrastructure.adapter.input.dto.request.EmailFileRequest;
import com.app.springxpert.email.infrastructure.adapter.input.dto.request.EmailRequest;
import com.app.springxpert.email.infrastructure.adapter.input.dto.request.EmailVerificationRequest;
import java.io.File;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements IEmailServicePort {

    private final IEmailPersistencePort emailPersistencePort;

    @Override
    public void sendEmail(EmailRequest emailRequest) {
        emailPersistencePort.sendEmail(emailRequest);
    }

    @Override
    public void sendEmailWithFile(EmailFileRequest emailFileRequest, File file) {
        emailPersistencePort.sendEmailWithFile(emailFileRequest, file);
    }

    @Override
    public void sendVerificationEmail(EmailVerificationRequest emailVerificationRequest) {
        emailPersistencePort.sendVerificationEmail(emailVerificationRequest);
    }
}
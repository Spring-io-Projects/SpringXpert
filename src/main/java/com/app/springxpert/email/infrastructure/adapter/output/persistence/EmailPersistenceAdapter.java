package com.app.springxpert.email.infrastructure.adapter.output.persistence;

import com.app.springxpert.email.application.port.output.persistence.IEmailPersistencePort;
import com.app.springxpert.email.infrastructure.adapter.input.dto.request.EmailFileRequest;
import com.app.springxpert.email.infrastructure.adapter.input.dto.request.EmailRequest;
import com.app.springxpert.email.infrastructure.adapter.input.dto.request.EmailVerificationRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailPersistenceAdapter implements IEmailPersistencePort {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmail(EmailRequest emailRequest) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(emailRequest.toUser());
        simpleMailMessage.setSubject(emailRequest.subject());
        simpleMailMessage.setText(emailRequest.message());

        javaMailSender.send(simpleMailMessage);
    }

    @Override
    public void sendEmailWithFile(EmailFileRequest emailFileRequest, File file) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());
            mimeMessageHelper.setTo(emailFileRequest.toUser());
            mimeMessageHelper.setSubject(emailFileRequest.subject());
            mimeMessageHelper.setText(emailFileRequest.message());
            mimeMessageHelper.addAttachment(file.getName(), file);

            javaMailSender.send(mimeMessage);
        }
        catch (MessagingException var5) {
            throw new RuntimeException(var5);
        }
    }

    @Override
    public void sendVerificationEmail(EmailVerificationRequest emailVerificationRequest) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(emailVerificationRequest.to());
            mimeMessageHelper.setSubject(emailVerificationRequest.subject());
            mimeMessageHelper.setText(emailVerificationRequest.verificationCode(), true);

            javaMailSender.send(mimeMessage);
        }
        catch (MessagingException var4) {
            throw new RuntimeException(var4);
        }
    }
}
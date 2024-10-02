package com.app.springxpert.email.infrastructure.adapter.input.controller;

import com.app.springxpert.email.application.port.input.service.IEmailServicePort;
import com.app.springxpert.email.infrastructure.adapter.input.dto.request.EmailFileRequest;
import com.app.springxpert.email.infrastructure.adapter.input.dto.request.EmailRequest;
import com.app.springxpert.shared.infrastructure.dto.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/api/v1/email"}, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Email", description = "Email Management")
public class EmailControllerAdapter {

    private final IEmailServicePort emailServicePort;

    @Transactional
    @PostMapping({"/send-message"})
    @Operation(
            summary = "Send a message",
            description = "Send a message by email"
    )
    public ResponseEntity<ResponseDTO> receiveRequestEmail(@RequestBody @Valid EmailRequest emailRequest) {
        emailServicePort.sendEmail(emailRequest);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseDTO("Send message successfully"));
    }

    @Transactional
    @PostMapping({"/send-message-file"})
    @Operation(
            summary = "Send a message with file",
            description = "Send a message by email with file - Try POSTMAN to send the file"
    )
    public ResponseEntity<ResponseDTO> receiveRequestEmailWithFile(@ModelAttribute @Valid EmailFileRequest emailFileRequest) {
        try {
            String fileName = emailFileRequest.file().getOriginalFilename();
            Path path = Paths.get("src/main/resources/emails/" + fileName);

            Files.createDirectories(path.getParent());
            Files.copy(emailFileRequest.file().getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            File file = path.toFile();

            emailServicePort.sendEmailWithFile(emailFileRequest, file);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseDTO("Send message with file successfully"));
        }
        catch (Exception e) {
            throw new RuntimeException("Error sending email with file. " + e.getMessage());
        }
    }
}
package com.app.springxpert.iam.infrastructure.adapter.input.controller;

import com.app.springxpert.iam.application.port.input.service.IUserServicePort;
import com.app.springxpert.iam.infrastructure.adapter.input.dto.request.ChangePasswordRequest;
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
@RequestMapping(value = {"/api/v1/user"}, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "User Management", description = "Endpoints for general user operations")
public class UserControllerAdapter {

    private final IUserServicePort userService;

    @Transactional
    @PostMapping({"/change-password"})
    @Operation(
            summary = "Change user password",
            description = "Change the password for the currently logged-in user"
    )
    public ResponseEntity<ResponseDTO> changePassword(@RequestBody @Valid ChangePasswordRequest changePasswordRequest) {
        userService.changePassword(changePasswordRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Password changed successfully"));
    }
}
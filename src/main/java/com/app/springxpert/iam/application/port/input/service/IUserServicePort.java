package com.app.springxpert.iam.application.port.input.service;

import com.app.springxpert.iam.infrastructure.adapter.input.dto.request.ChangePasswordRequest;

public interface IUserServicePort {
    void changePassword(ChangePasswordRequest changePasswordRequest);
    void checkPassword(String password);
}

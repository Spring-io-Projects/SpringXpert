package com.app.springxpert.iam.application.usecase;

import com.app.springxpert.iam.application.port.input.service.IUserDetailsServicePort;
import com.app.springxpert.iam.application.port.input.service.IUserServicePort;
import com.app.springxpert.iam.application.port.output.persistence.IUserPersistencePort;
import com.app.springxpert.iam.domain.model.aggregate.UserEntity;
import com.app.springxpert.iam.infrastructure.adapter.input.dto.request.ChangePasswordRequest;
import com.app.springxpert.shared.application.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.authentication.password.CompromisedPasswordDecision;
import org.springframework.security.authentication.password.CompromisedPasswordException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserServicePort {

    private final IUserDetailsServicePort userDetailsServicePort;
    private final IUserPersistencePort userPersistencePort;
    private final CompromisedPasswordChecker compromisedPasswordChecker;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Override
    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        Optional<UserEntity> userEntity = userDetailsServicePort.getCurrentUser();
        if (userEntity.isPresent()) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEntity.get().getUsername());
            checkPassword(changePasswordRequest.newPassword());
            if (!passwordEncoder.matches(changePasswordRequest.currentPassword(), userDetails.getPassword())) {
                throw new IllegalArgumentException("Incorrect current password");
            }
            else if (!changePasswordRequest.newPassword().equals(changePasswordRequest.confirmPassword())) {
                throw new IllegalArgumentException("Passwords do not match");
            }
            else {
                String newPassword = passwordEncoder.encode(changePasswordRequest.confirmPassword());
                userPersistencePort.changePassword(newPassword, userEntity.get().getId());
            }
        }
        else {
            throw new ResourceNotFoundException("User not logged in");
        }
    }

    @Override
    public void checkPassword(String password) {
        CompromisedPasswordDecision decision = compromisedPasswordChecker.check(password);
        if (decision.isCompromised()) {
            throw new CompromisedPasswordException("The password provided has been compromised.");
        }
    }
}
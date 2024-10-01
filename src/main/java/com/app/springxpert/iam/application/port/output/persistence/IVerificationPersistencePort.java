package com.app.springxpert.iam.application.port.output.persistence;

import com.app.springxpert.iam.domain.model.entity.VerificationEntity;
import java.util.Optional;

public interface IVerificationPersistencePort {
    void saveVerification(VerificationEntity verificationEntity);
    Optional<VerificationEntity> findVerificationEntityByCodeAndEmail(String code, String email);
}
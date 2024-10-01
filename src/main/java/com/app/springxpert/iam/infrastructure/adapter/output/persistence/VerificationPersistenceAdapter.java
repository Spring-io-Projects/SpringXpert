package com.app.springxpert.iam.infrastructure.adapter.output.persistence;

import com.app.springxpert.iam.application.port.output.persistence.IVerificationPersistencePort;
import com.app.springxpert.iam.domain.model.entity.VerificationEntity;
import com.app.springxpert.iam.infrastructure.adapter.output.repository.IVerificationRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationPersistenceAdapter implements IVerificationPersistencePort {
    private final IVerificationRepository verificationRepository;

    @Override
    public void saveVerification(VerificationEntity verificationEntity) {
        verificationRepository.save(verificationEntity);
    }

    @Override
    public Optional<VerificationEntity> findVerificationEntityByCodeAndEmail(String code, String email) {
        return verificationRepository.findVerificationEntityByCodeAndEmail(code, email);
    }
}

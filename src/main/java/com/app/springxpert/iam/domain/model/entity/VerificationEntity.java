package com.app.springxpert.iam.domain.model.entity;

import com.app.springxpert.iam.domain.model.aggregate.UserEntity;
import com.app.springxpert.iam.domain.model.valueobject.CodeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class VerificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 6, nullable = false)
    private String code;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    @Enumerated(EnumType.STRING)
    private CodeEnum status = CodeEnum.PENDING;

    @ManyToOne(targetEntity = UserEntity.class)
    private UserEntity user;
}
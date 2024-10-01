package com.app.springxpert.iam.application.port.input.service;

import com.app.springxpert.iam.domain.model.aggregate.UserEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public interface IUserDetailsServicePort {
    Optional<UserEntity> getCurrentUser();
    Authentication authenticate(String username, String password);
    List<SimpleGrantedAuthority> newAuthorities(UserEntity userEntity);
}
package com.app.springxpert.iam.application.usecase;

import com.app.springxpert.iam.application.port.input.service.IAuthServicePort;
import com.app.springxpert.iam.application.port.input.service.IUserDetailsServicePort;
import com.app.springxpert.iam.application.port.input.service.IUserServicePort;
import com.app.springxpert.iam.application.port.output.persistence.IRolePersistencePort;
import com.app.springxpert.iam.application.port.output.persistence.IUserPersistencePort;
import com.app.springxpert.iam.domain.model.aggregate.UserEntity;
import com.app.springxpert.iam.domain.model.entity.RoleEntity;
import com.app.springxpert.iam.domain.model.valueobject.RoleEnum;
import com.app.springxpert.iam.infrastructure.adapter.input.dto.request.LoginRequest;
import com.app.springxpert.iam.infrastructure.adapter.input.dto.request.SignupRequest;
import com.app.springxpert.iam.infrastructure.adapter.input.dto.response.AuthResponse;
import com.app.springxpert.iam.infrastructure.adapter.input.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthServicePort {

    private final IUserServicePort userServicePort;
    private final IUserDetailsServicePort userDetailsServicePort;
    private final IUserPersistencePort userPersistencePort;
    private final IRolePersistencePort rolePersistencePort;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponse signUp(SignupRequest signupRequest) {
        userServicePort.checkPassword(signupRequest.password());

        if (userPersistencePort.existsByEmailAndUsername(signupRequest.email(), signupRequest.username())) {
            throw new IllegalArgumentException("The email or username already exists.");
        }
        else {
            String email = signupRequest.email();
            String username = signupRequest.username();
            String password = signupRequest.password();

            List<RoleEnum> rolesRequest = signupRequest.roleList();
            Set<RoleEntity> roleEntityList = rolePersistencePort.findRoleEntitiesByRoleNameIn(rolesRequest);

            if (rolesRequest.contains(RoleEnum.DEVELOPER)) {
                throw new IllegalArgumentException("Cannot create a user with developer role only can be created by the creator.");
            }
            if (rolesRequest.contains(RoleEnum.ADMIN)) {
                throw new IllegalArgumentException("Cannot create a user with admin role only can be created by the developer. Roles permitted: [USER, INVITED]");
            }
            if (roleEntityList.isEmpty()) {
                throw new IllegalArgumentException("The roles specified does not exist.");
            }
            else {
                UserEntity userEntity = UserEntity.builder().email(email).username(username).password(passwordEncoder.encode(password)).roles(roleEntityList).isEnabled(false).accountNoLocked(true).accountNoExpired(true).credentialNoExpired(true).build();
                UserEntity userSaved = userPersistencePort.signUp(userEntity);

                List<SimpleGrantedAuthority> authorities = userDetailsServicePort.newAuthorities(userSaved);
                Authentication authentication = new UsernamePasswordAuthenticationToken(userSaved.getUsername(), userSaved.getPassword(), authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                String accessToken = jwtUtil.createToken(authentication);

                return new AuthResponse(username, "User created successfully", true, accessToken);
            }
        }
    }

    @Override
    public AuthResponse logIn(LoginRequest loginRequest) {
        String username = loginRequest.username();
        String password = loginRequest.password();

        Authentication authentication = userDetailsServicePort.authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtUtil.createToken(authentication);

        return new AuthResponse(username, "User logged successfully", true, accessToken);
    }
}
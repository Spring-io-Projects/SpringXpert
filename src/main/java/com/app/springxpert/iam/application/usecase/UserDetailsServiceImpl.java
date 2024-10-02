package com.app.springxpert.iam.application.usecase;

import com.app.springxpert.iam.application.port.input.service.IUserDetailsServicePort;
import com.app.springxpert.iam.application.port.output.persistence.IUserPersistencePort;
import com.app.springxpert.iam.domain.model.aggregate.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService, IUserDetailsServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userPersistencePort.findUserEntityByUsername(username);

        if (userEntity.isEmpty()) {
            throw new UsernameNotFoundException("El usuario " + username + " no existe.");
        }
        else {
            List<SimpleGrantedAuthority> authorityList = newAuthorities(userEntity.get());
            return new User(userEntity.get().getUsername(),
                    userEntity.get().getPassword(),
                    userEntity.get().isEnabled(),
                    userEntity.get().isAccountNoExpired(),
                    userEntity.get().isCredentialNoExpired(),
                    userEntity.get().isAccountNoLocked(),
                    authorityList
            );
        }
    }

    @Override
    public Optional<UserEntity> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = (String)authentication.getPrincipal();
            Optional<UserEntity> userEntity = userPersistencePort.findUserEntityByUsername(username);

            if (userEntity.isEmpty()) {
                throw new UsernameNotFoundException("El usuario " + username + " no existe.");
            }
            else {
                return userEntity;
            }
        }
        else {
            return Optional.empty();
        }
    }

    @Override
    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password");
        }
        else if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Incorrect Password");
        }
        else {
            return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
        }
    }

    @Override
    public List<SimpleGrantedAuthority> newAuthorities(UserEntity userEntity) {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        userEntity.getRoles().forEach(role ->
            authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleName().name()))));

        userEntity.getRoles().stream().flatMap(role -> role.getPermissionList().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        return authorityList;
    }
}
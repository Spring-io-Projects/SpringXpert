package com.app.springxpert;

import com.app.springxpert.iam.domain.model.aggregate.UserEntity;
import com.app.springxpert.iam.domain.model.entity.PermissionEntity;
import com.app.springxpert.iam.domain.model.entity.RoleEntity;
import com.app.springxpert.iam.domain.model.valueobject.RoleEnum;
import com.app.springxpert.iam.infrastructure.adapter.output.repository.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
@EnableJpaAuditing
@Slf4j
public class SpringxpertApplication {

    @Value("${user.default.email}")
    private String email;

    @Value("${user.default.username}")
    private String username;

    @Value("${user.default.password}")
    private String password;

    public static void main(String[] args) {
        SpringApplication.run(SpringxpertApplication.class, args);

        log.info("Swagger UI is available at » http://localhost:8080/swagger-ui/index.html");
        log.info("Console H2 is available at » http://localhost:8080/h2-console");
    }

    @Bean
    CommandLineRunner init(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            PermissionEntity createPermission = PermissionEntity.builder().name("CREATE").build();
            PermissionEntity readPermission = PermissionEntity.builder().name("READ ENDPOINT X").build();
            PermissionEntity updatePermission = PermissionEntity.builder().name("UPDATE ENDPOINT X").build();
            PermissionEntity deletePermission = PermissionEntity.builder().name("DELETE ENDPOINT X").build();

            RoleEntity roleAdmin = RoleEntity.builder()
                    .roleName(RoleEnum.ADMIN)
                    .permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission))
                    .build();

            UserEntity userWilverAR = UserEntity.builder()
                    .email(email)
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .isEnabled(true)
                    .accountNoExpired(true)
                    .accountNoLocked(true)
                    .credentialNoExpired(true)
                    .roles(Set.of(roleAdmin))
                    .build();

            userRepository.save(userWilverAR);
        };
    }
}

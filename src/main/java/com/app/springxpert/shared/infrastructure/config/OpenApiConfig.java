package com.app.springxpert.shared.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "SpringXpert - API Backend",
                description = "API Backend for SpringXpert Application. This API is used to manage the backend of the SpringXpert Application. " +
                        "It provides endpoints for managing users and other resources. " +
                        "For more information, visit: [GitHub Repository](https://github.com/Spring-io-Projects/SpringXpert).",
                termsOfService = "https://docs.github.com/en/github/site-policy/github-terms-of-service",
                version = "1.0.0",
                contact = @Contact(
                        name = "Wilver Arana Ramos",
                        url = "https://wilver-ar.web.app/",
                        email = "wilver.ar.dev@gmail.com"
                ),
                license = @License(
                        name = "Standard Software Use License for SpringXpertAPI",
                        url = "https://github.com/Spring-io-Projects/SpringXpert/blob/main/LICENSE"
                )
        ),
        servers = {
                @Server(description = "Development Server", url = "http://localhost:8080"),
                @Server(description = "Production Server", url = "https://springxpert.azurewebsites.net")
        },
        security = @SecurityRequirement(name = "Bearer Authentication")
)
@SecurityScheme(
        name = "Bearer Authentication",
        description = "JWT Bearer Token Authentication for accessing the API.",
        type = SecuritySchemeType.HTTP,
        paramName = "Authorization",
        in = SecuritySchemeIn.HEADER,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfig {  }
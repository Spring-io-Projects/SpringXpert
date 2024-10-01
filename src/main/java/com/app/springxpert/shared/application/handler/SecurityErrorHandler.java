package com.app.springxpert.shared.application.handler;

import java.util.HashMap;
import lombok.Generated;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.authorization.method.MethodAuthorizationDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class SecurityErrorHandler implements MethodAuthorizationDeniedHandler {
    @Generated
    private static final Logger log = LoggerFactory.getLogger(SecurityErrorHandler.class);

    public Object handleDeniedInvocation(MethodInvocation methodInvocation, AuthorizationResult authorizationResult) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("code", 401);
        response.put("message", "Not authorized");

        return response;
    }
}

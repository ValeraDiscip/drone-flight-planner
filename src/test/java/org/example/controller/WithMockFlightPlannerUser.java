package org.example.controller;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@WithSecurityContext(
        factory = WithCustomUserSecurityContextFactory.class
)
public @interface WithMockFlightPlannerUser {
    String username() default "username";
    String password() default "password";
    int id() default 1;
}

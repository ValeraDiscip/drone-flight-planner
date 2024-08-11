package org.example.controller;

import org.example.dto.FlightPlannerUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WithCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockFlightPlannerUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockFlightPlannerUser annotation) {
        FlightPlannerUser flightPlannerUser = new FlightPlannerUser(
                annotation.username(),
                annotation.password(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")),
                annotation.id()
        );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(
                new UsernamePasswordAuthenticationToken(flightPlannerUser, flightPlannerUser.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")))
        );

        return context;
    }
}

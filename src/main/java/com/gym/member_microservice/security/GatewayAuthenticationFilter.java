package com.gym.member_microservice.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class GatewayAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        
        if (request.getHeader("X-Internal-Call") != null) {
            System.out.println("Internal call detected, skipping authentication.");
            
            GatewayAuthenticationToken authToken = new GatewayAuthenticationToken(
            "internal-user",
            "internal",
            "internal@gym.com",
            List.of("ROLE_ADMIN")
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);

            filterChain.doFilter(request, response);
            return;
        }

        String authSource = request.getHeader("X-Auth-Source");

        if ("gateway".equals(authSource)) {
            String userId = request.getHeader("X-User-Id");
            String username = request.getHeader("X-User-Name");
            String email = request.getHeader("X-User-Email");
            String rolesHeader = request.getHeader("X-User-Roles");

            if (userId != null && !userId.isEmpty()) {
                List<String> roles = rolesHeader != null && !rolesHeader.isEmpty()
                        ? Arrays.asList(rolesHeader.split(","))
                        : List.of();

                GatewayAuthenticationToken authToken = new GatewayAuthenticationToken(
                        userId, username, email, roles);

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}

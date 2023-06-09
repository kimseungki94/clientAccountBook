package com.accountbook.presentation.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authentication = request.getHeader("ACCESS-TOKEN");

        if (authentication == null || !authentication.startsWith("Bearer ")) {
            request.setAttribute("exception", SecurityErrorCode.WRONG_TYPE_TOKEN);
            filterChain.doFilter(request, response);
            return;
        }

        String token = authentication.split(" ")[1];

        if (!JwtUtil.validateToken(token, secretKey)) {
            logger.error("Token 만료");
            request.setAttribute("exception", SecurityErrorCode.EXPIRED_TOKEN);
            filterChain.doFilter(request, response);
            return;
        }

        String email = JwtUtil.getEmail(token, secretKey);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, null, List.of(new SimpleGrantedAuthority("USER")));

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}

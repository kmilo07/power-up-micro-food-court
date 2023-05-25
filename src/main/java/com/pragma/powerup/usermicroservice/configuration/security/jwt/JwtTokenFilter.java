package com.pragma.powerup.usermicroservice.configuration.security.jwt;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.PrincipalUser;
import com.pragma.powerup.usermicroservice.configuration.security.exception.TokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.*;

public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    JwtProvider jwtProvider;


    private final List<String> excludedPrefixes = Arrays.asList("/swagger-ui/**", "/actuator/**","/category");
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
            throws ServletException, IOException {
        String token = getToken(req);
        if (token != null && jwtProvider.tokenIsValid(token)) {
            String role = getRole(token);
            String email = getEmail(token);
            UserDetails user = PrincipalUser.build(email,role);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, req.getHeader(HttpHeaders.AUTHORIZATION),
                    user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        else if(token != null){
            throw new TokenException();
        }
        filterChain.doFilter(req, res);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String currentRoute = request.getServletPath();
        for (String prefix : excludedPrefixes) {
            if (pathMatcher.matchStart(prefix, currentRoute)) {
                return true;
            }
        }
        return false;
    }

    private String getToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7); // return everything after "Bearer "
        }
        return null;
    }

    private String getRole(String token){
        String decodePayload = getDecodePayload(token);
        String role;
        ObjectMapper mapper = new ObjectMapper();
        try {
            role = mapper.readValue(decodePayload, HashMap.class).get("roles").toString();
        } catch (JsonProcessingException e) {
            throw new TokenException();
        }
        return role.replace("[","").replace("]","");
    }

    private String getEmail(String token){
        String decodePayload = getDecodePayload(token);
        String email;
        ObjectMapper mapper = new ObjectMapper();
        try {
            email = mapper.readValue(decodePayload, HashMap.class).get("sub").toString();
        } catch (JsonProcessingException e) {
            throw new TokenException();
        }

        return email;
    }

    private String getDecodePayload(String token){
        String payload = token.split("\\.")[1];
        return  new String(Base64.getDecoder().decode(payload));
    }
}

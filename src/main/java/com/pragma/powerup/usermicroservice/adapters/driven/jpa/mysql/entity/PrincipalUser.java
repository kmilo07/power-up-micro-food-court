package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PrincipalUser implements UserDetails {
    private final String email;

    private final Collection<? extends GrantedAuthority> authority;

    public PrincipalUser(String email,Collection<? extends GrantedAuthority> authority) {
        this.email = email;
        this.authority = authority;
    }

    public static PrincipalUser build(String email, String role) {
        List<GrantedAuthority> authority = Collections.singletonList(new SimpleGrantedAuthority(role));
        return new PrincipalUser(email,authority);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authority;
    }

    @Override
    public String getPassword() {
        return "12";
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

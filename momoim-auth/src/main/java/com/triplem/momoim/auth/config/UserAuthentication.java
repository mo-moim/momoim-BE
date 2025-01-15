package com.triplem.momoim.auth.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.Objects;

public class UserAuthentication extends UsernamePasswordAuthenticationToken {
    private final String role;

    public UserAuthentication(Long principal, String role) {
        super(principal, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role)));
        this.role = role;
    }

    public UserAuthentication(Object principal, Object credentials, String role) {
        super(principal, credentials, Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role)));
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        UserAuthentication that = (UserAuthentication) o;

        return Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }
}

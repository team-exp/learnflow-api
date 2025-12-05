package com.teamexp.learnflowapi.user.config;

import com.teamexp.learnflowapi.user.model.User;
import com.teamexp.learnflowapi.user.model.vo.UserRole;
import java.util.Collection;
import java.util.UUID;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;

@Getter
public class CustomUserPrincipal implements UserDetails {

    private final String id;
    private final String email;
    private final String password;
    private final UserRole role;

    public CustomUserPrincipal(User user) {
        this.id = user.getUserId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.role = user.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }
}
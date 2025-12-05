package com.teamexp.learnflowapi.user.model;

import com.teamexp.learnflowapi.user.model.vo.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.UUID;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import java.util.List;

import java.time.OffsetDateTime;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET del_flag = true WHERE user_id = ?")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private String userId;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING) // 추후에 Enum이 추가될 경우를 대비
    @Column(name = "role", nullable = false)
    private UserRole role;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @Column(name = "del_flag", nullable = false)
    private Boolean delFlag = false;

    // JPA를 위한 기본 생성자
    protected User() {}

    private User(String email, String password, String nickname, UserRole role) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
        this.delFlag = false;
    }

    public static User createUser( String email, String password, String nickname, UserRole role) {
        return new User(email, password, nickname, role);
    }

    // UserDetails의 메서드들
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}

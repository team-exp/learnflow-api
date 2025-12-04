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
import org.hibernate.annotations.SQLDelete;

import java.time.OffsetDateTime;

@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET del_flag = true WHERE user_id = ?")
public class User {

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

    public static User createUser( String nickname, String email, String password, UserRole role) {
        return new User(nickname, email, password, role);
    }
}

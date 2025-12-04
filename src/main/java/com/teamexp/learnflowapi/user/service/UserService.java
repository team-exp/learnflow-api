package com.teamexp.learnflowapi.user.service;

import com.teamexp.learnflowapi.user.config.PasswordConfig;
import com.teamexp.learnflowapi.user.controller.dto.UserCreateRequest;
import com.teamexp.learnflowapi.user.model.User;
import com.teamexp.learnflowapi.user.model.vo.UserRole;
import com.teamexp.learnflowapi.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordConfig passwordConfig;

    @Autowired
    public UserService(UserRepository userRepository, PasswordConfig passwordConfig) {
        this.userRepository = userRepository;
        this.passwordConfig = passwordConfig;
    }

    @Transactional
    public void register(UserCreateRequest request) {
        boolean existsEmail = userRepository.existsByEmail(request.email());
        if (existsEmail) {
            // TODO : Global Exception으로 변경
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        // password 암호화
        String encodedPassword = passwordConfig.passwordEncoder().encode(request.password());


        // TODO : user의 Role은 Member밖에 없는 상황
        User user = User.createUser(request.nickname(), request.email(), encodedPassword, UserRole.MEMBER);

    }
}

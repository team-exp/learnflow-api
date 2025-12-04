package com.teamexp.learnflowapi.user.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UserCreateRequest(
        @Email
        @NotNull(message = "Email cannot be null")
        String email,

        @NotNull(message = "Password cannot be null")
        String password,

        @NotNull(message = "Nickname cannot be null")
        String nickname
) {

}

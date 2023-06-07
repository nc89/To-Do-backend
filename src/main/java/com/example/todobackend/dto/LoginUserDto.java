package com.example.todobackend.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
@Getter @Setter
public class LoginUserDto {
    @NotBlank(message = "username is mandatory")
    private String username;
    @NotBlank(message = "username is mandatory")
    private String password;

    public LoginUserDto() {
    }

    public LoginUserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

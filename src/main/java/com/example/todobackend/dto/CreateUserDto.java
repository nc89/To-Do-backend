package com.example.todobackend.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
@Getter @Setter
public class CreateUserDto {
    @NotBlank(message = "Username is mandatory")
    private String username;
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email")
    private String email;
    @NotBlank(message = "Username is mandatory")
    private String password;

    List<String> roles = new ArrayList<>();

    public CreateUserDto() {
    }

    public CreateUserDto(String username, String email, String password, List<String> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
}

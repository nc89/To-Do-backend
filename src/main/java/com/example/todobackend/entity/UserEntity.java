package com.example.todobackend.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class UserEntity {
    private String id;
    private String username;
    private String email;
    private String password;
}

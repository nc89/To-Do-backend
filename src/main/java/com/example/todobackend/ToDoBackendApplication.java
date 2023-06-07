package com.example.todobackend;

import com.example.todobackend.service.UserEntityService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
public class ToDoBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToDoBackendApplication.class, args);
    }

}

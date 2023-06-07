package com.example.todobackend.controller;

import com.example.todobackend.dto.CreateUserDto;
import com.example.todobackend.dto.JwtTokenDto;
import com.example.todobackend.dto.LoginUserDto;
import com.example.todobackend.dto.MessageDto;
import com.example.todobackend.entity.UserEntity;
import com.example.todobackend.exceptions.AttributeException;
import com.example.todobackend.service.UserDetailsServiceImpl;
import com.example.todobackend.service.UserEntityService;
import com.example.todobackend.service.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private UserEntityService userEntityService;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @PostMapping("/create")
    public ResponseEntity<MessageDto> create(@Valid @RequestBody CreateUserDto dto) throws AttributeException {
        UserEntity userEntity = userEntityService.create(dto);
        return ResponseEntity.ok(new MessageDto(HttpStatus.OK, "user " + userEntity.getUsername() + " have been created"));
    }

    @PostMapping("/create-admin")
    public ResponseEntity<MessageDto> createAdmin(@Valid @RequestBody CreateUserDto dto) throws AttributeException {
        UserEntity userEntity = userEntityService.createAdmin(dto);
        return ResponseEntity.ok(new MessageDto(HttpStatus.OK, "admin " + userEntity.getUsername() + " have been created"));
    }

    @PostMapping("/create-user")
    public ResponseEntity<MessageDto> createUser(@Valid @RequestBody CreateUserDto dto) throws AttributeException {
        UserEntity userEntity = userEntityService.createUser(dto);
        return ResponseEntity.ok(new MessageDto(HttpStatus.OK, "user " + userEntity.getUsername() + " have been created"));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> login(@Valid @RequestBody LoginUserDto dto) throws AttributeException {
        JwtTokenDto jwtTokenDto = userEntityService.login(dto);
        return ResponseEntity.ok(jwtTokenDto);
    }

    @GetMapping("/current-user")
    public UserPrincipal obteinCurrentUser(Principal principal){
        return (UserPrincipal) userDetailsServiceImpl.loadUserByUsername(principal.getName());
    }
}

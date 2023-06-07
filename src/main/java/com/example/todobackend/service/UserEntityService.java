package com.example.todobackend.service;

import com.example.todobackend.config.jwt.JwtProvider;
import com.example.todobackend.dto.CreateUserDto;
import com.example.todobackend.dto.JwtTokenDto;
import com.example.todobackend.dto.LoginUserDto;
import com.example.todobackend.entity.UserEntity;
import com.example.todobackend.enums.RoleEnum;
import com.example.todobackend.exceptions.AttributeException;
import com.example.todobackend.repository.UserEntityRepository;
import com.example.todobackend.utils.Operations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserEntityService {
    @Autowired
    private UserEntityRepository userEntityRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private AuthenticationManager authenticationManager;

    public UserEntity create(CreateUserDto dto) throws AttributeException {
        if(userEntityRepository.existsByUsername(dto.getUsername()))
            throw new AttributeException("Username already in use");
        if(userEntityRepository.existsByEmail(dto.getEmail()))
            throw new AttributeException("Email already in use");
        if(dto.getRoles().isEmpty())
            throw new AttributeException("Roles are mandatory");
        return userEntityRepository.save(mapUserFromDto(dto));
    }

    public UserEntity createAdmin(CreateUserDto dto) throws AttributeException {
        if(userEntityRepository.existsByUsername(dto.getUsername()))
            throw new AttributeException("Username already in use");
        if(userEntityRepository.existsByEmail(dto.getEmail()))
            throw new AttributeException("Email already in use");

        List<String> roles = Arrays.asList("ROLE_ADMIN", "ROLE_USER");
        dto.setRoles(roles);
        return userEntityRepository.save(mapUserFromDto(dto));
    }
    public UserEntity createUser(CreateUserDto dto) throws AttributeException {
        if(userEntityRepository.existsByUsername(dto.getUsername()))
            throw new AttributeException("Username already in use");
        if(userEntityRepository.existsByEmail(dto.getEmail()))
            throw new AttributeException("Email already in use");

        List<String> roles = Arrays.asList("ROLE_USER");
        dto.setRoles(roles);
        return userEntityRepository.save(mapUserFromDto(dto));
    }

    public JwtTokenDto login(LoginUserDto dto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        return new JwtTokenDto(token);
    }

    // private methods
    private UserEntity mapUserFromDto(CreateUserDto dto) {
        Long id = Operations.autoIncrement(userEntityRepository.findAll());
        String password = passwordEncoder.encode(dto.getPassword());
        List<RoleEnum> roles =
                dto.getRoles().stream().map(rol -> RoleEnum.valueOf(rol)).collect(Collectors.toList());
        return new UserEntity(id, dto.getUsername(), dto.getEmail(), password, roles);
    }
}

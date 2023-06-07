package com.example.todobackend.service;

import com.example.todobackend.entity.UserEntity;
import com.example.todobackend.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserEntityRepository userEntityRepository;

    // busca si  el email o username existe
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userEntityRepository.findByUsernameOrEmail(username, username);
        if (!userEntity.isPresent())
            throw new UsernameNotFoundException("Not Exists");
        return UserPrincipal.build(userEntity.get());
    }
}

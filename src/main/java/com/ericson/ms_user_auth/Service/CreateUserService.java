package com.ericson.ms_user_auth.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ericson.ms_user_auth.Domain.DTOS.UserResponseDTO;
import com.ericson.ms_user_auth.Domain.Entity.UserEntity;
import com.ericson.ms_user_auth.Exception.UserAlreadyExistsException;
import com.ericson.ms_user_auth.Repository.UserRepository;

@Service
public class CreateUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponseDTO execute(UserEntity userEntity) {

        var user = this.userRepository.findByEmail(userEntity.getEmail()).orElseThrow(() -> {
            throw new UserAlreadyExistsException();
        });

        var password = passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(password);

        var userDTO = UserResponseDTO.builder().id(user.getId()).email(user.getEmail()).username(user.getUsername())
                .build();

        return userDTO;
    }
}

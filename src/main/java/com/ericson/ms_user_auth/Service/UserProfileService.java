package com.ericson.ms_user_auth.Service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ericson.ms_user_auth.Domain.DTOS.UserResponseDTO;
import com.ericson.ms_user_auth.Repository.UserRepository;

public class UserProfileService {

    @Autowired
    private UserRepository userRepository;

    public UserResponseDTO findById(UUID idUser) {

        var user = this.userRepository.findById(idUser).orElseThrow(() -> {
            throw new UsernameNotFoundException("User not found.");
        });

        var userDTO = UserResponseDTO.builder().id(user.getId()).email(user.getEmail()).username(user.getUsername())
                .build();

        return userDTO;
    }

    public UserResponseDTO findByUsername(String userName) {

        var user = this.userRepository.findByUsername(userName).orElseThrow(() -> {
            throw new UsernameNotFoundException("Username not found.");
        });

        var userDTO = UserResponseDTO.builder().id(user.getId()).email(user.getEmail()).username(user.getUsername())
                .build();

        return userDTO;
    }

    public UserResponseDTO findByEmail(String userEmail) {

        var user = this.userRepository.findByEmail(userEmail).orElseThrow(() -> {
            throw new UsernameNotFoundException("Email not found.");
        });

        var userDTO = UserResponseDTO.builder().id(user.getId()).email(user.getEmail()).username(user.getUsername())
                .build();

        return userDTO;
    }
}

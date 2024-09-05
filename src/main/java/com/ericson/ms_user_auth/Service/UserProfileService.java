package com.ericson.ms_user_auth.Service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ericson.ms_user_auth.Domain.DTOS.UserRegisterResponseDTO;
import com.ericson.ms_user_auth.Repository.UserRepository;

public class UserProfileService {

    @Autowired
    private UserRepository userRepository;

    public UserRegisterResponseDTO findById(UUID idUser) {

        var user = this.userRepository.findById(idUser).orElseThrow(() -> {
            throw new UsernameNotFoundException("User not found.");
        });

        var userDTO = UserRegisterResponseDTO.builder().id(user.getId()).email(user.getEmail())
                .username(user.getUsername())
                .build();

        return userDTO;
    }

    public UserRegisterResponseDTO findByUsername(String userName) {

        var user = this.userRepository.findByUsername(userName).orElseThrow(() -> {
            throw new UsernameNotFoundException("Username not found.");
        });

        var userDTO = UserRegisterResponseDTO.builder().id(user.getId()).email(user.getEmail())
                .username(user.getUsername())
                .build();

        return userDTO;
    }

    public UserRegisterResponseDTO findByEmail(String userEmail) {

        var user = this.userRepository.findByEmail(userEmail).orElseThrow(() -> {
            throw new UsernameNotFoundException("Email not found.");
        });

        var userDTO = UserRegisterResponseDTO.builder().id(user.getId()).email(user.getEmail())
                .username(user.getUsername())
                .build();

        return userDTO;
    }
}

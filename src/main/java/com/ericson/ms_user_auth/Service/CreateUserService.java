package com.ericson.ms_user_auth.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ericson.ms_user_auth.Domain.Entity.UserEntity;
import com.ericson.ms_user_auth.Exception.UserFoundException;
import com.ericson.ms_user_auth.Repository.UserRepository;

@Service
public class CreateUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity execute(UserEntity userEntity) {

        this.userRepository.findByUsernameOrEmail(userEntity.getUsername(), userEntity.getEmail())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });

        var password = passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(password);

        return this.userRepository.save(userEntity);
    }
}

package com.ericson.ms_user_auth.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;

import com.auth0.jwt.algorithms.Algorithm;
import com.ericson.ms_user_auth.Domain.DTOS.AuthUserRequestDTO;
import com.ericson.ms_user_auth.Domain.DTOS.AuthUserResponseDTO;
import com.ericson.ms_user_auth.Repository.UserRepository;

@Service
public class AuthenticationUserService {

    @Value("${jwt.user.secret}")
    private String secretKey;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthUserResponseDTO execute(AuthUserRequestDTO authUserDTO) throws AuthenticationException {
        var user = this.userRepository.findByEmail(authUserDTO.email()).orElseThrow(() -> {
            throw new UsernameNotFoundException("Email/password incorrect");
        });

        var passwordMatches = this.passwordEncoder.matches(authUserDTO.password(), user.getPassword());

        if (!passwordMatches) {
            throw new AuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        var expiresIn = Instant.now().plus(Duration.ofHours(2));

        var token = JWT.create()
                .withIssuer("java")
                .withSubject(user.getId().toString())
                .withExpiresAt(expiresIn)
                .sign(algorithm);

        var authUserResponse = AuthUserResponseDTO.builder()
                .access_token(token)
                .expires_in(expiresIn.toEpochMilli())
                .build();

        return authUserResponse;
    }
}

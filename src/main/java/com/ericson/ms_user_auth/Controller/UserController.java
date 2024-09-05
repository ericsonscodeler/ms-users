package com.ericson.ms_user_auth.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ericson.ms_user_auth.Domain.DTOS.AuthUserRequestDTO;
import com.ericson.ms_user_auth.Domain.DTOS.UserRegisterResponseDTO;
import com.ericson.ms_user_auth.Domain.Entity.UserEntity;
import com.ericson.ms_user_auth.Service.AuthenticationUserService;
import com.ericson.ms_user_auth.Service.CreateUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private CreateUserService createUserService;

    @Autowired
    private AuthenticationUserService authenticationUserService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody UserEntity userEntity) {
        try {
            var result = this.createUserService.execute(userEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/auth")
    public ResponseEntity<Object> auth(@RequestBody AuthUserRequestDTO authUserDTO) {
        try {
            var token = this.authenticationUserService.execute(authUserDTO);
            return ResponseEntity.ok().body(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}

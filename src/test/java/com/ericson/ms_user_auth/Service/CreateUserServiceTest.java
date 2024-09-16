package com.ericson.ms_user_auth.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;

import java.util.Optional;
import java.util.UUID;

import com.ericson.ms_user_auth.Domain.DTOS.UserRegisterResponseDTO;
import com.ericson.ms_user_auth.Domain.Entity.UserEntity;
import com.ericson.ms_user_auth.Exception.UserAlreadyExistsException;
import com.ericson.ms_user_auth.Repository.UserRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class CreateUserServiceTest {

    @InjectMocks
    private CreateUserService createUserService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("Should be able to create a new user")
    public void should_be_able_to_create_a_new_user() {

        UserEntity user = new UserEntity();
        user.setUsername("test");
        user.setEmail("teste@teste.com");
        user.setPassword("password123");
        var userId = UUID.randomUUID();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword123");

        UserEntity savedUser = new UserEntity();
        savedUser.setId(userId);
        savedUser.setUsername("test");
        savedUser.setEmail("teste@teste.com");

        when(userRepository.save(user)).thenReturn(savedUser);

        UserRegisterResponseDTO createdUser = createUserService.execute(user);

        assertThat(createdUser).isEqualTo(new UserRegisterResponseDTO(userId, "teste@teste.com", "test"));
    }

    @Test
    @DisplayName("Should throw UserAlreadyExistsException if email already exists")
    public void should_throw_exception_if_email_already_exists() {

        UserEntity existingUser = new UserEntity();
        existingUser.setUsername("existingUser");
        existingUser.setEmail("existing@teste.com");
        existingUser.setPassword("password");

        when(userRepository.findByEmail("existing@teste.com")).thenReturn(Optional.of(existingUser));

        UserEntity newUser = new UserEntity();
        newUser.setUsername("newUser");
        newUser.setEmail("existing@teste.com");
        newUser.setPassword("newpassword");

        Assertions.assertThrows(UserAlreadyExistsException.class, () -> {
            createUserService.execute(newUser);
        });

        verify(userRepository).findByEmail("existing@teste.com");
        verify(userRepository, never()).save(newUser);
    }
}
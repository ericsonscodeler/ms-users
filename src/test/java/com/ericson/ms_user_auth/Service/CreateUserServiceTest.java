package com.ericson.ms_user_auth.Service;

import static org.assertj.core.api.Assertions.assertThat;
import com.ericson.ms_user_auth.Domain.Entity.UserEntity;
import com.ericson.ms_user_auth.Repository.UserRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class CreateUserServiceTest {

    @InjectMocks
    private CreateUserService createUserService;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("Should be able to create a new user")
    public void should_be_able_to_create_a_new_user() {
        var userName = "test";
        var userPassword = "password123"
        try {
            this.createUserService
        } catch (Exception e) {
            // TODO: handle exception
        }

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo("test");
    }
}
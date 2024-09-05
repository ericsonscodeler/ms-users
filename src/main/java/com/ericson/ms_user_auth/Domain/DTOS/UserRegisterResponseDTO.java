package com.ericson.ms_user_auth.Domain.DTOS;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserRegisterResponseDTO {

    private UUID id;
    private String email;
    private String username;
}

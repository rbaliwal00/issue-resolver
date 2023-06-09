package com.rbaliwal00.todoappusingjsp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    @NotNull(message = "username should not be null")
    @Email
    private String email;

    @NotNull(message = "password should not be null")
    private String password;
}

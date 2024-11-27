package com.dashtap.DASHTAP.payload.requests;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @Email(message = "Not valid email address")
    private String email;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}

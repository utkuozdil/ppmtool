package com.utku.ppmtool.payload;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {

    @NotBlank(message = "Username can't be blank")
    private String username;

    @NotBlank(message = "Password can't be blank")
    private String password;
}

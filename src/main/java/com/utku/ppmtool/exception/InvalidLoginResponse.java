package com.utku.ppmtool.exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
public class InvalidLoginResponse {

    public InvalidLoginResponse() {
        this.username = "Invalid Username";
        this.password = "Invalid password";
    }

    @NonNull
    private String username;

    @NonNull
    private String password;
}

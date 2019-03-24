package com.utku.ppmtool.exception;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@Data
@RequiredArgsConstructor
public class UsernameAlreadyExistsExceptionResponse {

    @NonNull
    private String username;
}

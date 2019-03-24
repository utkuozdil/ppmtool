package com.utku.ppmtool.payload;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class JwtLoginSuccessResponse {

    @NonNull
    private boolean success;

    @NonNull
    private String token;
}

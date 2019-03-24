package com.utku.ppmtool.exception;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ProjectIdExceptionResponse {

    @NonNull
    private String projectIdentifier;
}

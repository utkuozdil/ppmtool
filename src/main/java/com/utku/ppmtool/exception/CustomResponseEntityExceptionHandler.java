package com.utku.ppmtool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<Object> handleProjectIdException(ProjectIdException e, WebRequest webRequest) {
        ProjectIdExceptionResponse projectIdExceptionResponse = new ProjectIdExceptionResponse(e.getMessage());
        return new ResponseEntity<>(projectIdExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleProjectNotFoundException(ProjectNotFoundException e, WebRequest webRequest) {
        ProjectNotFoundExceptionResponse projectNotFoundExceptionResponse = new ProjectNotFoundExceptionResponse(e.getMessage());
        return new ResponseEntity<>(projectNotFoundExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException e, WebRequest webRequest) {
        UsernameAlreadyExistsExceptionResponse usernameAlreadyExistsExceptionResponse = new UsernameAlreadyExistsExceptionResponse(e.getMessage());
        return new ResponseEntity<>(usernameAlreadyExistsExceptionResponse, HttpStatus.BAD_REQUEST);
    }
}

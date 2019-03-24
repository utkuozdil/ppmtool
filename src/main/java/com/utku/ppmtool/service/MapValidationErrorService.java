package com.utku.ppmtool.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
public class MapValidationErrorService {

    public ResponseEntity<?> getErrorResponseEntity(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fieldError : bindingResult.getFieldErrors())
                errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
        } else
            return null;
    }
}

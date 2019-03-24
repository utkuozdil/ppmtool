package com.utku.ppmtool.web;

import com.utku.ppmtool.domain.User;
import com.utku.ppmtool.payload.JwtLoginSuccessResponse;
import com.utku.ppmtool.payload.LoginRequest;
import com.utku.ppmtool.security.JwtTokenProvider;
import com.utku.ppmtool.service.MapValidationErrorService;
import com.utku.ppmtool.service.UserService;
import com.utku.ppmtool.validator.UserValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.utku.ppmtool.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/users")
public class UserController {

    public UserController(MapValidationErrorService mapValidationErrorService, UserService userService, UserValidator userValidator,
                          JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.mapValidationErrorService = mapValidationErrorService;
        this.userService = userService;
        this.userValidator = userValidator;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    private final MapValidationErrorService mapValidationErrorService;

    private final UserService userService;

    private final UserValidator userValidator;

    private JwtTokenProvider jwtTokenProvider;

    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
        ResponseEntity<?> errorResponseEntity = mapValidationErrorService.getErrorResponseEntity(bindingResult);
        if (errorResponseEntity != null)
            return errorResponseEntity;
        else {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                    loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);
            return ResponseEntity.ok(new JwtLoginSuccessResponse(true, jwt));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        ResponseEntity<?> errorResponseEntity = mapValidationErrorService.getErrorResponseEntity(bindingResult);
        if (errorResponseEntity != null)
            return errorResponseEntity;
        else {
            user = userService.saveUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
    }
}

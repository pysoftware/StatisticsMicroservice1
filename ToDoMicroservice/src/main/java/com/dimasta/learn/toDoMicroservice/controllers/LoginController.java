package com.dimasta.learn.toDoMicroservice.controllers;

import com.dimasta.learn.toDoMicroservice.entities.User;
import com.dimasta.learn.toDoMicroservice.jsonResponses.JwtResponse;
import com.dimasta.learn.toDoMicroservice.jsonResponses.SimpleMessageResponse;
import com.dimasta.learn.toDoMicroservice.jsonResponses.ValidationErrorResponse;
import com.dimasta.learn.toDoMicroservice.services.AuthService;
import com.dimasta.learn.toDoMicroservice.services.AuthServiceImpl;
import com.dimasta.learn.toDoMicroservice.utilities.JsonResponseBody;
import com.dimasta.learn.toDoMicroservice.utilities.UserNotLoggedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final AuthService authService;

    @Autowired
    public LoginController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
        @Valid User user,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(UNAUTHORIZED)
                .body(new ValidationErrorResponse(UNAUTHORIZED, bindingResult.getFieldErrors()));
        }

        try {
            String jwtToken = authService.tryLogIn(user);
            return ResponseEntity.ok(new JwtResponse(OK, jwtToken));
        } catch (UserNotLoggedException exception) {
            return ResponseEntity.status(UNAUTHORIZED)
                .body(new SimpleMessageResponse(UNAUTHORIZED, exception.getMessage()));
        }
    }

}

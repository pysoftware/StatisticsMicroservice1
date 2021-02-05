package com.dimasta.learn.toDoMicroservice.controllers;

import com.dimasta.learn.toDoMicroservice.entities.User;
import com.dimasta.learn.toDoMicroservice.jsonResponses.JwtResponse;
import com.dimasta.learn.toDoMicroservice.jsonResponses.SimpleMessageResponse;
import com.dimasta.learn.toDoMicroservice.jsonResponses.ValidationErrorResponse;
import com.dimasta.learn.toDoMicroservice.services.AuthService;
import com.dimasta.learn.toDoMicroservice.services.AuthServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RestController
public class RegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    final AuthService authService;

    @Autowired
    public RegistrationController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(BAD_REQUEST)
                .body(new ValidationErrorResponse(BAD_REQUEST, bindingResult.getFieldErrors()));
        }

        User newUser = null;
        try {
            newUser = authService.register(user);
        } catch (Exception exception) {
            return ResponseEntity.status(BAD_REQUEST)
                .body(new SimpleMessageResponse(BAD_REQUEST, exception.getMessage()));
        }
        logger.info(newUser.toString());
        String jwt = authService.generateJwtToken(user);

        return ResponseEntity.status(OK)
            .header("jwt", jwt)
            .body(new JwtResponse(OK, jwt));
    }
}

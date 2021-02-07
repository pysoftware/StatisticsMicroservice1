package com.dimasta.learn.toDoMicroservice.controllers;

import com.dimasta.learn.toDoMicroservice.entities.User;
import com.dimasta.learn.toDoMicroservice.jsonResponses.JwtResponse;
import com.dimasta.learn.toDoMicroservice.jsonResponses.SimpleMessageResponse;
import com.dimasta.learn.toDoMicroservice.jsonResponses.ValidationErrorResponse;
import com.dimasta.learn.toDoMicroservice.services.AuthService;
import com.dimasta.learn.toDoMicroservice.services.AuthServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
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

    final AmqpTemplate amqpTemplate;

    @Autowired
    public RegistrationController(AuthServiceImpl authService, AmqpTemplate amqpTemplate) {
        this.authService = authService;
        this.amqpTemplate = amqpTemplate;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@Valid User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(BAD_REQUEST)
                    .body(new ValidationErrorResponse(BAD_REQUEST, bindingResult.getFieldErrors()));
        }

        try {
            User newUser = authService.register(user);
            logger.info(newUser.toString());
            String jwt = authService.generateJwtToken(newUser);

            amqpTemplate.convertAndSend("new_user", newUser);

            return ResponseEntity.status(OK)
                    .header("jwt", jwt)
                    .body(new JwtResponse(OK, jwt));
        } catch (Exception exception) {
            logger.info(exception.getMessage());
            return ResponseEntity.status(BAD_REQUEST)
                    .body(new SimpleMessageResponse(BAD_REQUEST, exception.getMessage()));
        }
    }
}

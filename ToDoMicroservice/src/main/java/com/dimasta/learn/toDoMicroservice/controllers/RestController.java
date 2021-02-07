package com.dimasta.learn.toDoMicroservice.controllers;

import com.dimasta.learn.toDoMicroservice.entities.ToDo;
import com.dimasta.learn.toDoMicroservice.entities.User;
import com.dimasta.learn.toDoMicroservice.jsonResponses.SimpleMessageResponse;
import com.dimasta.learn.toDoMicroservice.jsonResponses.SuccessfullyResponse;
import com.dimasta.learn.toDoMicroservice.jsonResponses.ValidationErrorResponse;
import com.dimasta.learn.toDoMicroservice.repositories.ToDoRepository;
import com.dimasta.learn.toDoMicroservice.services.AuthService;
import com.dimasta.learn.toDoMicroservice.services.ToDoService;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    final ToDoRepository toDoRepository;

    final AuthService authService;

    final ToDoService toDoService;

    final AmqpTemplate amqpTemplate;

    @Autowired
    public RestController(ToDoRepository toDoRepository, AuthService authService, ToDoService toDoService, AmqpTemplate amqpTemplate) {
        this.toDoRepository = toDoRepository;
        this.authService = authService;
        this.toDoService = toDoService;
        this.amqpTemplate = amqpTemplate;
    }

    @GetMapping(value = "/test")
    @ResponseBody
    public String test() {
        return "string";
    }

    @GetMapping(value = "/todos")
    public ResponseEntity<?> showToDos(HttpServletRequest request) {
        try {
            User user = authService.validateRequestJwt(request);
            List<ToDo> toDoList = toDoRepository.findDistinctToDoByUser_Id(user.getId());
            return ResponseEntity
                    .status(OK)
                    .body(new SimpleMessageResponse(OK, toDoList));
        } catch (ExpiredJwtException e3) {
            return ResponseEntity
                    .status(GATEWAY_TIMEOUT)
                    .body(new SimpleMessageResponse(GATEWAY_TIMEOUT, "Session Expired: " + e3.toString()));
        }
    }

    @PostMapping(value = "/todos")
    public ResponseEntity<?> newToDo(HttpServletRequest request, @Valid ToDo toDo, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(BAD_REQUEST)
                    .body(new ValidationErrorResponse(BAD_REQUEST, bindingResult.getFieldErrors()));
        }

        try {
            User user = authService.validateRequestJwt(request);
            ToDo newestToDo = toDoService.addToDo(toDo, user);

            logger.info(newestToDo.toString());

            amqpTemplate.convertAndSend("new_todo", newestToDo);

            return ResponseEntity
                    .status(OK)
                    .body(new SuccessfullyResponse());
        } catch (ExpiredJwtException e3) {
            return ResponseEntity
                    .status(GATEWAY_TIMEOUT)
                    .body(new SimpleMessageResponse(GATEWAY_TIMEOUT, "Session Expired: " + e3.toString()));
        }
    }

    @DeleteMapping(value = "/todos/{id}")
    public ResponseEntity<?> deleteToDo(HttpServletRequest request, @PathVariable(name = "id") Long toDoId) {
        try {
            User user = authService.validateRequestJwt(request);
            toDoService.deleteToDo(toDoId, user);
            return ResponseEntity
                    .status(OK)
                    .body(new SuccessfullyResponse());
        } catch (ExpiredJwtException e3) {
            return ResponseEntity
                    .status(GATEWAY_TIMEOUT)
                    .body(new SimpleMessageResponse(GATEWAY_TIMEOUT, "Session Expired: " + e3.toString()));
        }
    }
}

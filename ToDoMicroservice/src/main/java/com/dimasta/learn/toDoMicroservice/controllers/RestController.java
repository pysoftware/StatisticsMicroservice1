package com.dimasta.learn.toDoMicroservice.controllers;

import com.dimasta.learn.toDoMicroservice.entities.ToDo;
import com.dimasta.learn.toDoMicroservice.entities.User;
import com.dimasta.learn.toDoMicroservice.jsonResponses.ValidationErrorResponse;
import com.dimasta.learn.toDoMicroservice.repositories.ToDoRepository;
import com.dimasta.learn.toDoMicroservice.services.AuthService;
import com.dimasta.learn.toDoMicroservice.services.ToDoService;
import com.dimasta.learn.toDoMicroservice.utilities.JsonResponseBody;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    final ToDoRepository toDoRepository;

    final AuthService authService;

    final ToDoService toDoService;

    @Autowired
    public RestController(ToDoRepository toDoRepository, AuthService authService, ToDoService toDoService) {
        this.toDoRepository = toDoRepository;
        this.authService = authService;
        this.toDoService = toDoService;
    }

//    @GetMapping("/todos")
//    public ResponseEntity<JsonResponseBody> showToDos(HttpServletRequest request) {
//
//        //1) success: arraylist of ToDos in the "response" attribute of the JsonResponseBody
//        //2) fai: error message
//        try {
//            Map<String, Object> userData = loginService.verifyJwtAndGetData(request);
//            return ResponseEntity.status(HttpStatus.OK)
//                .body(new JsonResponseBody(HttpStatus.OK.value(), toDoRepository.findDistinctToDoByUser_Email((String) userData.get("email"))));
//        } catch (UserNotLoggedException e2) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new JsonResponseBody(HttpStatus.FORBIDDEN.value(), "Forbidden: " + e2.toString()));
//        } catch (ExpiredJwtException e3) {
//            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(new JsonResponseBody(HttpStatus.GATEWAY_TIMEOUT.value(), "Session Expired: " + e3.toString()));
//        }
//    }

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
            return ResponseEntity.status(HttpStatus.OK)
                .body(new JsonResponseBody(HttpStatus.OK.value(), newestToDo));
        } catch (ExpiredJwtException e3) {
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(new JsonResponseBody(HttpStatus.GATEWAY_TIMEOUT.value(), "Session Expired: " + e3.toString()));
        }
    }

//    @DeleteMapping("/todos/{id}")
//    public ResponseEntity<JsonResponseBody> deleteToDo(HttpServletRequest request, @PathVariable(name = "id") Long toDoId) {
//
//        try {
//            loginService.verifyJwtAndGetData(request);
//            toDoService.deleteToDo(toDoId);
//            return ResponseEntity.status(HttpStatus.OK).body(new JsonResponseBody(HttpStatus.OK.value(), "ToDo correctly delete"));
//        } catch (UserNotLoggedException e2) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new JsonResponseBody(HttpStatus.FORBIDDEN.value(), "Forbidden: " + e2.toString()));
//        } catch (ExpiredJwtException e3) {
//            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(new JsonResponseBody(HttpStatus.GATEWAY_TIMEOUT.value(), "Session Expired: " + e3.toString()));
//        }
//    }


}

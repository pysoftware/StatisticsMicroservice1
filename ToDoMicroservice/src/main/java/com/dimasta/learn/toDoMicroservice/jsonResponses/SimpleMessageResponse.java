package com.dimasta.learn.toDoMicroservice.jsonResponses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public class SimpleMessageResponse extends CustomResponse {
    public SimpleMessageResponse(HttpStatus httpStatus, Object message) {
        super(httpStatus);
        this.message = message;
    }

    @Getter
    private final Object message;
}

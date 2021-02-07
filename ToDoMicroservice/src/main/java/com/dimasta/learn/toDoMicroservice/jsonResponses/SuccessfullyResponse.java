package com.dimasta.learn.toDoMicroservice.jsonResponses;

import static org.springframework.http.HttpStatus.OK;

public class SuccessfullyResponse extends CustomResponse {
    public SuccessfullyResponse() {
        super(OK);
    }
}

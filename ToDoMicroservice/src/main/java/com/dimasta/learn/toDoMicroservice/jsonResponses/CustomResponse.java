package com.dimasta.learn.toDoMicroservice.jsonResponses;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@NoArgsConstructor
public abstract class CustomResponse {
    private HttpStatus status;

    public int getStatus() {
        return status.value();
    }
}

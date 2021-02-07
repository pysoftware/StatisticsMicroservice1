package com.dimasta.learn.statisticsMicroservice.jsonResponses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public class JwtResponse extends CustomResponse {
    public JwtResponse(HttpStatus httpStatus, String jwtToken) {
        super(httpStatus);
        this.jwtToken = jwtToken;
    }

    @Getter
    private final String jwtToken;
}

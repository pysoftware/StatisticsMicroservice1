package com.dimasta.learn.toDoMicroservice.utilities;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class JsonResponseBody {
    private  int status;
    private Object response;
}
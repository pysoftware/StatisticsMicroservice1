package com.dimasta.learn.statisticsMicroservice.jsonResponses;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class ValidationErrorResponse extends CustomResponse {
    public ValidationErrorResponse(HttpStatus status, List<FieldError> fieldErrors) {
        super(status);
        this.fieldErrors = fieldErrors;
    }

    private final Map<String, String> errors = new HashMap<>();
    private final List<FieldError> fieldErrors;

    public Map<String, String> getErrors() {
        fieldErrors.forEach(e -> {
            errors.put(e.getField(), e.getDefaultMessage());
        });
        return errors;
    }

}

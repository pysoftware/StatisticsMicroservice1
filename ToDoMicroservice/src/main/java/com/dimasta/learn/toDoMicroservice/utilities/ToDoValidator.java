package com.dimasta.learn.toDoMicroservice.utilities;

import com.dimasta.learn.toDoMicroservice.entities.ToDo;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ToDoValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ToDo.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        ToDo toDo = (ToDo) obj;

        ToDoPriority priority = toDo.getPriority();

        if (!"high".equals(priority.getPriorityType()) && !"low".equals(priority.getPriorityType())) {
            errors.rejectValue("priority", "Priority must be 'high' or 'low'!");
        }

    }
}


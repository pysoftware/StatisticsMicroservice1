package com.dimasta.learn.toDoMicroservice.services;

import com.dimasta.learn.toDoMicroservice.entities.ToDo;
import com.dimasta.learn.toDoMicroservice.entities.User;

public interface ToDoService {
    ToDo addToDo(ToDo toDo, User user);

    void deleteToDo(Long id);
}

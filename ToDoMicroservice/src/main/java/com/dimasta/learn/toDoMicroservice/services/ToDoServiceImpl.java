package com.dimasta.learn.toDoMicroservice.services;

import com.dimasta.learn.toDoMicroservice.entities.ToDo;
import com.dimasta.learn.toDoMicroservice.entities.User;
import com.dimasta.learn.toDoMicroservice.repositories.ToDoRepository;
import org.springframework.stereotype.Service;

@Service
public class ToDoServiceImpl implements ToDoService {

    final ToDoRepository toDoRepository;

    public ToDoServiceImpl(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    @Override
    public ToDo addToDo(ToDo toDo, User user){
        toDo.setUser(user);
        return toDoRepository.save(toDo);
    }

    @Override
    public void deleteToDo(Long id){
        toDoRepository.delete(id);
    }
}

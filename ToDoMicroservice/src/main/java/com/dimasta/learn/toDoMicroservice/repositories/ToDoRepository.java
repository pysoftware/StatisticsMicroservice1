package com.dimasta.learn.toDoMicroservice.repositories;

import com.dimasta.learn.toDoMicroservice.entities.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Long> {
    List<ToDo> findDistinctToDoByUser_Id(Long userId);

    @Transactional
    void deleteToDoByIdAndUserId(Long toDoId, Long userId);
}

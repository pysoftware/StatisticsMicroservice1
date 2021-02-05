package com.dimasta.learn.toDoMicroservice.repositories;

import com.dimasta.learn.toDoMicroservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserById(Long id);

    Optional<User> findFirstUserByEmailAndPassword(String email, String password);

    Optional<User> findUserByEmail(String email);
}

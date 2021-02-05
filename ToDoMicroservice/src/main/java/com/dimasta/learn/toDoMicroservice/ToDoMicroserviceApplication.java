package com.dimasta.learn.toDoMicroservice;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Log
@SpringBootApplication
public class ToDoMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToDoMicroserviceApplication.class, args);
    }

}

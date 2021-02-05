package com.dimasta.learn.toDoMicroservice.utilities;

public class UserNotInDatabaseException extends Exception {

    public UserNotInDatabaseException(String message){
        super(message);
    }
}

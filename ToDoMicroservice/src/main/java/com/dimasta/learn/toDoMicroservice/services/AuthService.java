package com.dimasta.learn.toDoMicroservice.services;

import com.dimasta.learn.toDoMicroservice.entities.User;
import com.dimasta.learn.toDoMicroservice.utilities.UserNotLoggedException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface AuthService {
    User register(User user) throws Exception;

    String tryLogIn(User user) throws UserNotLoggedException;

    String generateJwtToken(User user);

    User validateRequestJwt(HttpServletRequest request) throws ExpiredJwtException, UnsupportedJwtException;
}

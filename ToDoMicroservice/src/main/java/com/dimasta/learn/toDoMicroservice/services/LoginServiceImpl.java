package com.dimasta.learn.toDoMicroservice.services;

import com.dimasta.learn.toDoMicroservice.repositories.UserRepository;
import com.dimasta.learn.toDoMicroservice.utilities.EncryptionUtils;
import com.dimasta.learn.toDoMicroservice.utilities.UserNotInDatabaseException;
import com.dimasta.learn.toDoMicroservice.utilities.UserNotLoggedException;
import com.dimasta.learn.toDoMicroservice.entities.User;
import com.dimasta.learn.toDoMicroservice.utilities.JwtUtils;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    final UserRepository userRepository;

    final EncryptionUtils encryptionUtils;

    final
    JwtUtils jwtUtils;

    @Autowired
    public LoginServiceImpl(UserRepository userRepository, EncryptionUtils encryptionUtils, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.encryptionUtils = encryptionUtils;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Optional<User> getUserFromDb(String email, String pwd) throws UserNotInDatabaseException {
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isPresent()) {
            if (!encryptionUtils.decrypt(user.get().getPassword()).equals(pwd)) {
                throw new UserNotInDatabaseException("Wrong Email or Password");
            }
        } else {
            throw new UserNotInDatabaseException("Wrong Email or Password");
        }
        return user;
    }

    @Override
    public String createJwt(String email, String name) {
        Date date = new Date();
        date.setTime(date.getTime() + (300 * 1000));
        return jwtUtils.generateJwt(email, name, date);
    }

    @Override
    public Map<String, Object> verifyJwtAndGetData(HttpServletRequest request) throws UserNotLoggedException {
        String jwt = jwtUtils.getJwtFromHttpRequest(request);
        if (jwt == null) {
            throw new UserNotLoggedException("User not logged.");
        }
        return jwtUtils.jwt2Map(jwt);
    }

}

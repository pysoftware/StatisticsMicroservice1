package com.dimasta.learn.toDoMicroservice.services;

import com.dimasta.learn.toDoMicroservice.controllers.LoginController;
import com.dimasta.learn.toDoMicroservice.entities.User;
import com.dimasta.learn.toDoMicroservice.repositories.UserRepository;
import com.dimasta.learn.toDoMicroservice.utilities.UserNotLoggedException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final UserRepository userRepository;
    private static final String JWT_SECRET_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9FAH49FMAHF";

    @Autowired
    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(User user) throws Exception {

        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new Exception("User have already been created");
        }

        User newUser = new User(user.getEmail(), user.getName(), DigestUtils.md5Hex(user.getPassword()));
        userRepository.save(newUser);

        return newUser;
    }

    public String tryLogIn(User tryingUser) throws UserNotLoggedException {
        Optional<User> user = userRepository.findFirstUserByEmailAndPassword(
                tryingUser.getEmail(),
                DigestUtils.md5Hex(tryingUser.getPassword())
        );
        if (user.isPresent()) {
            return this.generateJwtToken(user.get());
        } else {
            throw new UserNotLoggedException("Wrong email or password");
        }
    }

    @Override
    public String generateJwtToken(User user) {
        Date expirationTokenTime = new Date();
        expirationTokenTime.setTime(expirationTokenTime.getTime() + (300 * 1000));
        Key key = Keys.hmacShaKeyFor(JWT_SECRET_KEY.getBytes());
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(expirationTokenTime)
                .addClaims(Map.of(
                        "name", user.getName(),
                        "id", user.getId()
                ))
                .signWith(key)
                .compact();
    }


    @Override
    public User validateRequestJwt(HttpServletRequest request) throws ExpiredJwtException, UnsupportedJwtException {
        String jwt = request.getHeader("Authorization").replace("Bearer ", "");
        Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8)).build().parseClaimsJws(jwt);
        Long userId = claimsJws.getBody().get("id", Long.class);
        return userRepository.findUserById(userId);
    }
}

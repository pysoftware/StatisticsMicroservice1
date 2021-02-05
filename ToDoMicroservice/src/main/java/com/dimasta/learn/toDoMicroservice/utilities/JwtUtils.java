package com.dimasta.learn.toDoMicroservice.utilities;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This class provides method in order to generate and validate JSON Web Tokens
 */
@Component
public class JwtUtils {

    private final String JWT_SECRET_KEY = "secret_key_1";

    /* USEFUL LINKS:
        https://stormpath.com/blog/jwt-java-create-verify
        https://stormpath.com/blog/beginners-guide-jwts-in-java
        https://github.com/jwtk/jjwt
    */

    public String generateJwt(String email, String name, Date date) {
        return Jwts.builder()
            .setSubject(email)
            .setExpiration(date)
            .claim("name", name)
            .signWith(
                SignatureAlgorithm.HS256,
                JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8)
            )
            .compact();
    }


    public Map<String, Object> jwt2Map(String jwt) throws ExpiredJwtException {

        Jws<Claims> claim = Jwts.parser()
            .setSigningKey(JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8))
            .parseClaimsJws(jwt);

        String name = claim.getBody().get("name", String.class);

        Date expDate = claim.getBody().getExpiration();
        String email = claim.getBody().getSubject();

        Map<String, Object> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("name", name);
        userData.put("exp_date", expDate);

        Date now = new Date();
        if (now.after(expDate)) {
            throw new ExpiredJwtException(null, null, "Session expired!");
        }

        return userData;
    }


    /**
     * this method extracts the jwt from the header or the cookie in the Http request
     *
     * @return jwt
     */
    public String getJwtFromHttpRequest(HttpServletRequest request) {
        String jwt = null;
        if (request.getHeader("Authorization") != null) {
            jwt = request.getHeader("Authorization").replace("Bearer ", "");     //token present in header
        } else if (request.getCookies() != null) {
            Cookie[] cookies = request.getCookies();   //token present in cookie
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("jwt")) {
                    jwt = cookie.getValue();
                }
            }
        }
        return jwt;
    }

}

package com.ericson.ms_user_auth.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    private final Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
    private final JWTVerifier verifier = JWT.require(algorithm).build();

    public String generateToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 600000)) // 10 min
                .sign(algorithm);
    }

    public DecodedJWT validateToken(String token) throws JWTVerificationException {
        return verifier.verify(token);
    }

    public String getUsernameFromToken(String token) {
        DecodedJWT decodedJWT = validateToken(token);
        return decodedJWT.getSubject();
    }
}

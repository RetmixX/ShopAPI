package com.example.retmix.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.retmix.dto.users.UserDTO;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class TokenUtil {

    public String generateToken(UserDTO userDTO) throws NoSuchAlgorithmException {
        KeyPair key = initKeyPair();
        Algorithm algorithm = Algorithm
                .RSA256((RSAPublicKey) key.getPublic(), (RSAPrivateKey) key.getPrivate());

        return JWT.create().withIssuer(userDTO.toString()).sign(algorithm);
    }

    public boolean checkToken(String token, UserDTO userDTO) throws NoSuchAlgorithmException {
        KeyPair key = initKeyPair();
        try{
            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) key.getPublic(), (RSAPrivateKey) key.getPrivate());
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(userDTO.toString()).build();
            verifier.verify(token);
            return true;
        }catch (JWTVerificationException ex){
            return false;
        }
    }

    private KeyPair initKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        return keyPairGenerator.generateKeyPair();
    }
}

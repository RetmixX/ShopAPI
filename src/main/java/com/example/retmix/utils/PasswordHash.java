package com.example.retmix.utils;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
@Component
public class PasswordHash {

    public String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(password.getBytes());
        byte[] passwordBytes = messageDigest.digest();
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b: passwordBytes){
            stringBuilder.append(String.format("%02x", b & 0xff));
        }

        return stringBuilder.toString();
    }

    public boolean checkPassword(String password, String hashPassword) throws NoSuchAlgorithmException {
        String passwordCheck = hashPassword(password);
        return passwordCheck.equals(hashPassword);
    }
}

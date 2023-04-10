package com.example.retmix.exceptions;

public class UserByTokenNotFountError extends ApiException{
    public UserByTokenNotFountError(String message) {
        super(message);
    }
}

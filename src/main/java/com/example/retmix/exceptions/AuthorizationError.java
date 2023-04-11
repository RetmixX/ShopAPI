package com.example.retmix.exceptions;

public class AuthorizationError extends ApiException{
    public AuthorizationError(String message) {
        super(message);
    }
}

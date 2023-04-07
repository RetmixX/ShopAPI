package com.example.retmix.exceptions;

public class ObjectNotFoundError extends ApiException{

    public ObjectNotFoundError(String message) {
        super(message);
    }
}

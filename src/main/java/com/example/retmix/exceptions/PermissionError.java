package com.example.retmix.exceptions;

public class PermissionError extends ApiException{
    public PermissionError(String message) {
        super(message);
    }
}

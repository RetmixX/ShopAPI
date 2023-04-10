package com.example.retmix.exceptions;

public class PermissionDenied extends ApiException {
    public PermissionDenied(String message) {
        super(message);
    }
}

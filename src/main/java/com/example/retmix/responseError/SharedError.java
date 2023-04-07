package com.example.retmix.responseError;

public class SharedError {
    private final String message;
    private final int statusCode;

    public SharedError(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }
}

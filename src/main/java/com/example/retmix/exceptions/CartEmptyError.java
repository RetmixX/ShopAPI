package com.example.retmix.exceptions;

public class CartEmptyError extends ApiException{
    public CartEmptyError(String message) {
        super(message);
    }
}

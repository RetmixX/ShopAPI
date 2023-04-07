package com.example.retmix.responseError;

import com.example.retmix.exceptions.ApiException;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;

public class ValidationError{
    private int code;
    private String message;
    private List<Map<String, List<String>>> errors;

    public ValidationError(List<Map<String, List<String>>> errors) {
        this.code = 422;
        this.message = "Validation error";
        this.errors = errors;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<Map<String, List<String>>> getErrors() {
        return errors;
    }
}

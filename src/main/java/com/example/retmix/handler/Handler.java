package com.example.retmix.handler;

import com.example.retmix.exceptions.*;
import com.example.retmix.responseError.SharedError;
import com.example.retmix.responseError.ValidationError;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice(basePackages = "com.example.retmix")
public class Handler {
    @ExceptionHandler(RegistrationError.class)
    public ResponseEntity<SharedError> registrationError(RegistrationError ex){
        return ResponseEntity.status(409).body(new SharedError(ex.getMessage(), 409));
    }

    @ExceptionHandler(ObjectNotFoundError.class)
    public ResponseEntity<SharedError> objectNotFound(ObjectNotFoundError ex){
        return ResponseEntity.status(404).body(new SharedError(ex.getMessage(), 404));
    }

    @ExceptionHandler(UserByTokenNotFountError.class)
    public ResponseEntity<?> tokenNotValid(UserByTokenNotFountError ex){
        return ResponseEntity.status(401).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<SharedError> paramsError(MethodArgumentTypeMismatchException ex){
        return ResponseEntity.badRequest().body(new SharedError("Переданный параметр не валиден", 400));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validationError(MethodArgumentNotValidException ex){
        var errors = ex.getFieldErrors().stream().map(FieldError::getField).collect(Collectors.toSet())
                .stream().map(error->Map.of(error, ex.getFieldErrors(error)
                        .stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList()))
                .collect(Collectors.toList());

        return ResponseEntity.status(422).body(Map.of("error", new ValidationError(errors)));
    }

    @ExceptionHandler(NoSuchAlgorithmException.class)
    public ResponseEntity<?> generateTokenError(NoSuchAlgorithmException ex){
        return ResponseEntity.status(500).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(PermissionDenied.class)
    public ResponseEntity<?> permissionDenied(PermissionDenied ex){
        return ResponseEntity.status(403).body(Map.of("error", Map.of("message", ex.getMessage())));
    }
}

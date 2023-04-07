package com.example.retmix.handler;

import com.example.retmix.exceptions.IdiNaxyiException;
import com.example.retmix.exceptions.ObjectNotFoundError;
import com.example.retmix.exceptions.RegistrationError;
import com.example.retmix.responseError.SharedError;
import com.example.retmix.responseError.ValidationError;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class Handler {

    @ExceptionHandler(IdiNaxyiException.class)
    public ResponseEntity<SharedError> idiNaxyiException(IdiNaxyiException ex){
        return ResponseEntity.status(415).body(new SharedError(ex.getMessage(), 415));
    }

    @ExceptionHandler(RegistrationError.class)
    public ResponseEntity<SharedError> registrationError(RegistrationError ex){
        return ResponseEntity.status(409).body(new SharedError(ex.getMessage(), 409));
    }

    @ExceptionHandler(ObjectNotFoundError.class)
    public ResponseEntity<SharedError> objectNotFound(ObjectNotFoundError ex){
        return ResponseEntity.status(404).body(new SharedError(ex.getMessage(), 404));
    }

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
}

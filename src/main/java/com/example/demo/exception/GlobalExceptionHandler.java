package com.example.demo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(
                        error.getField(),
                        error.getDefaultMessage()
                )
        );

        Map<String, Object> response = Map.of(
                "message", "Błąd walidacji",
                "errors", errors
        );

        return ResponseEntity.badRequest().body(response);
    }

    // HTTP 404
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleNotFound(ResponseStatusException ex) {
        Map<String, Object> response = Map.of(
                "status", ex.getStatusCode().value(),
                "message", ex.getReason()
        );

        return ResponseEntity.status(ex.getStatusCode()).body(response);
    }
}
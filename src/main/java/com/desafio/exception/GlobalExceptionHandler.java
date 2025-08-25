package com.desafio.exception;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RequestNotPermitted.class)
    public ResponseEntity<?> handleRateLimit(RequestNotPermitted ex) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .body(java.util.Map.of(
                    "erro", "Limite de requisições excedido. Tente novamente em instantes.",
                    "detalhe", ex.getMessage()
                ));
    }
    // ... outros handlers ...
}

package org.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.test.client.exception.ProductClientException;
import org.test.client.exception.ProductNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 🔴 404 - recurso no encontrado
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ProductNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildError("404", "Resource Not Found", ex.getMessage()));
    }

    // 🔴 400 - stock insuficiente
    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorResponse> handleStock(InsufficientStockException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildError("400", "Insufficient Stock", ex.getMessage()));
    }

    // 🔴 400 - validaciones (DTOs)
    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            org.springframework.web.bind.MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildError("400", "Validation Error", message));
    }

    // 🔴 500 - error genérico
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildError("500", "Internal Server Error", ex.getMessage()));
    }

    @ExceptionHandler(ProductClientException.class)
    public ResponseEntity<ErrorResponse> handleProductClient(ProductClientException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(buildError(
                        String.valueOf(ex.getStatus()),
                        "Product Service Error",
                        ex.getMessage()
                ));
    }

    // 🔧 builder JSON API
    private ErrorResponse buildError(String status, String title, String detail) {
        return new ErrorResponse(
                List.of(new ApiError(status, title, detail)),
                LocalDateTime.now()
        );
    }
}

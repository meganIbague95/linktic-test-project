package com.test.product_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleProductNotFound(ProductNotFoundException ex) {

        ApiError error = ApiError.builder()
                .status("404")
                .title("Product Not Found")
                .detail(ex.getMessage())
                .build();

        return ErrorResponse.builder()
                .errors(List.of(error))
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneric(Exception ex) {

        ApiError error = ApiError.builder()
                .status("500")
                .title("Internal Server Error")
                .detail(ex.getMessage())
                .build();

        return ErrorResponse.builder()
                .errors(List.of(error))
                .build();
    }
}

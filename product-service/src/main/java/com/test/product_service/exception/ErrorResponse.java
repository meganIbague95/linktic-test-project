package com.test.product_service.exception;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ErrorResponse {
    private List<ApiError> errors;
}

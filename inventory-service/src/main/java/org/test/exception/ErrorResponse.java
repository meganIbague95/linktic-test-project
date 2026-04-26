package org.test.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ErrorResponse {
    private List<ApiError> errors;
    private LocalDateTime timestamp;
}

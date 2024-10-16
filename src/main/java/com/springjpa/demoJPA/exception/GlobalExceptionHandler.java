package com.springjpa.demoJPA.exception;

import com.springjpa.demoJPA.dto.request.ApiResponse;
import jakarta.validation.ConstraintViolation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
    public class GlobalExceptionHandler {
        private static final String MIN_ATTRIBUTE = "min";

        @ExceptionHandler(value = Exception.class)
        ResponseEntity<ApiResponse> runtimeExceptionHandler(RuntimeException e) {
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
            apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
            return ResponseEntity.badRequest().body(apiResponse);
        }

        @ExceptionHandler(value = MethodArgumentNotValidException.class)
        ResponseEntity<ApiResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
            ApiResponse apiResponse = new ApiResponse();
            String enumKey = e.getFieldError().getDefaultMessage();

            ErrorCode errorCode = ErrorCode.INVALID_KEY;

            Map<String, Object> attributes = null;
            try {
                errorCode = ErrorCode.valueOf(enumKey);

                var constrainViolation = e.getBindingResult()
                        .getAllErrors().getFirst().unwrap(ConstraintViolation.class);

                attributes = constrainViolation.getConstraintDescriptor().getAttributes();

            }catch (IllegalArgumentException ex) {

            }

            apiResponse.setCode(errorCode.getCode());
            apiResponse.setMessage(Objects.nonNull(attributes) ?
                    mapAttribute(errorCode.getMessage(), attributes)
                    : errorCode.getMessage());

            return ResponseEntity.badRequest().body(apiResponse);
        }

        @ExceptionHandler(value = AppException.class)
        ResponseEntity<ApiResponse> AppExceptionHandler(AppException e) {
            ErrorCode errorCode = e.getErrorCode();
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setCode(errorCode.getCode());
            apiResponse.setMessage(errorCode.getMessage());
            return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
        }

        @ExceptionHandler(value = AccessDeniedException.class)
        ResponseEntity<ApiResponse> AccessDeniedExceptionHandler(AccessDeniedException e) {
            ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
            return ResponseEntity.status(errorCode.getStatusCode()).body(
                    ApiResponse.builder()
                            .code(errorCode.getCode())
                            .message(errorCode.getMessage())
                            .build()
            );
        }

        private String mapAttribute(String message, Map<String, Object> attributes) {
            String minValue = attributes.get(MIN_ATTRIBUTE).toString();
            return message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
        }
}

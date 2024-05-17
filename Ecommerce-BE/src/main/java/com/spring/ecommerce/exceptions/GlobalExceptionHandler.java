package com.spring.ecommerce.exceptions;


import com.spring.ecommerce.dto.ErrorDetailsDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // global exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetailsDto> handleGlobalException(Exception exception,
                                                                 WebRequest webRequest) {
        ErrorDetailsDto ErrorDetailsDto = new ErrorDetailsDto(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false)
        );
        return new ResponseEntity<>(ErrorDetailsDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetailsDto> handleResourceNotFoundException(
            ResourceNotFoundException exception,
            WebRequest webRequest
    ) {
        ErrorDetailsDto ErrorDetailsDto = new ErrorDetailsDto(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false)
        );
        return new ResponseEntity<>(ErrorDetailsDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetailsDto> handleAccessDeniedException(AccessDeniedException exception,
                                                                       WebRequest webRequest) {
        ErrorDetailsDto ErrorDetailsDto = new ErrorDetailsDto(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false)
        );
        return new ResponseEntity<>(ErrorDetailsDto, HttpStatus.UNAUTHORIZED);
    }
}

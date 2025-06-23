package com.learn.app.exception.handler;

import com.learn.app.exception.RecipeNotFoundException;
import com.learn.app.exception.RecipeServiceException;
import com.learn.app.model.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;

@RestControllerAdvice
public class RecipeApplicationExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(RecipeApplicationExceptionHandler.class);

    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity<Object> handleRecipeNotFoundException(RecipeNotFoundException ex) {
        logger.error("Recipe not found: {}", ex.getMessage());
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RecipeServiceException.class)
    public ResponseEntity<Object> handleRecipeServiceException(RecipeServiceException ex) {
        logger.error("Recipe service error: {}", ex.getMessage());
        return buildErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        logger.error("An unexpected error occurred: {}", ex.getMessage());
        return buildErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> buildErrorResponse(String message, HttpStatus httpStatus) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(OffsetDateTime.now())
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .message(message)
                .build();
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

}

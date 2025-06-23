package com.learn.app.exception;

public class RecipeServiceException extends RuntimeException{
    public RecipeServiceException(String message) {
        super(message);
    }

    public RecipeServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

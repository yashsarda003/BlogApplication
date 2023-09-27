package com.blogApp.BlogApplication.exception;

import org.springframework.stereotype.Component;

@Component
public class CustomException extends RuntimeException {
    private String ErrorType;
    private String ErrorMessage;

    public CustomException(String errorType, String errorMessage) {
        ErrorType = errorType;
        ErrorMessage = errorMessage;
    }

    public CustomException() {

    }

    public String getErrorType() {
        return ErrorType;
    }

    public void setErrorType(String errorType) {
        ErrorType = errorType;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }
}

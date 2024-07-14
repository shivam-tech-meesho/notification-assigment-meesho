package com.meesho.notification_system.advice;

import com.meesho.notification_system.dto.res.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionsHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleInvalidArgument(MethodArgumentNotValidException ex) {
        StringBuilder errMessage = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            if(!errMessage.isEmpty()) errMessage.append(", ");
            errMessage.append(error.getDefaultMessage());
        });
        return new ErrorResponse(HttpStatus.BAD_REQUEST.toString(),errMessage.toString());
    }
}
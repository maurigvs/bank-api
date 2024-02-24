package com.maurigvs.bank.account.exception;

import com.maurigvs.bank.account.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.stream.Collectors;

@ControllerAdvice
public class AccountExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleEntityNotFound(EntityNotFoundException exception){
        return new ErrorResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException exception){
        var message = exception.getFieldErrors().stream()
                .map(error -> ("[" + error.getField() + "] " + error.getDefaultMessage()))
                .collect(Collectors.joining("; "));
        return new ErrorResponse(HttpStatus.BAD_REQUEST.getReasonPhrase(), message);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleRuntime(RuntimeException exception){
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                exception.getClass().getSimpleName() + ": " + exception.getMessage());
    }

}

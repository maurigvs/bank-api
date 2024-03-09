package com.maurigvs.bank.accountholder.exception;

import com.maurigvs.bank.accountholder.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleNoSuchElement(NoSuchElementException exception){
        log.error(exception.getClass().getSimpleName(), exception);

        return new ErrorResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException exception){
        log.error(exception.getClass().getSimpleName(), exception);

        var message = exception.getFieldErrors().stream()
                .map(error -> ("[" + error.getField() + "] " + error.getDefaultMessage()))
                .collect(Collectors.joining("; "));

        return new ErrorResponse(HttpStatus.BAD_REQUEST.getReasonPhrase(), message);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleRuntime(RuntimeException exception){
        log.error(exception.getClass().getSimpleName(), exception);
        var message = exception.getClass().getSimpleName() + ": " + exception.getMessage();

        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), message);
    }
}

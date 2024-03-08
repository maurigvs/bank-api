package com.maurigvs.bank.transaction.exception;

import com.maurigvs.bank.transaction.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TransactionExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(TransactionExceptionHandler.class);

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleEntityNotFound(EntityNotFoundException exception){
        log.error(exception.getClass().getSimpleName(), exception);
        return new ErrorResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), exception.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleRuntime(RuntimeException exception){
        log.error(exception.getClass().getSimpleName(), exception);
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                exception.getClass().getSimpleName() + ": " + exception.getMessage());
    }
}

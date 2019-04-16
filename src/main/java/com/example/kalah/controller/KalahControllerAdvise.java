package com.example.kalah.controller;

import com.example.kalah.exception.KalahException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * This class is created to manage the exceptions of the controller(s)
 */
@RestControllerAdvice
@Slf4j
public class KalahControllerAdvise {


    /**
     * handles KalahExceptions
     *
     * @param e the exception as cause
     */
    @ExceptionHandler(KalahException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected void handle(KalahException e) {
        log.error("HttpStatus.INTERNAL_SERVER_ERROR", e);
    }

    /**
     * handles Runtime exceptions
     *
     * @param e the exception as cause
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected void handle(Exception e) {
        log.error("HttpStatus.INTERNAL_SERVER_ERROR", e);
    }

}

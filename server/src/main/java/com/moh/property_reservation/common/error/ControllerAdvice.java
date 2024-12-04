package com.moh.property_reservation.common.error;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ControllerAdvice {

    private final ObjectFactory<ProblemBuilder> problemBuilder;

    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Problem> handleException(HttpMediaTypeNotSupportedException ex) {
        var problem = problemBuilder.getObject()
                .title("http-media-type-not-supported")
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        log.info("HttpMediaTypeNotSupportedException (%s): %s".formatted(ex.getMessage(), problem), ex);
        return problem.toResponse();
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<Problem> handleException(HttpMessageNotReadableException ex) {
        var problem = problemBuilder.getObject()
                .title("http-message-not-readable")
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        log.info("HttpMessageNotReadableException (%s): %s".formatted(ex.getMessage(), problem), ex);
        return problem.toResponse();
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Problem> handleException(Exception ex) {
        var problem = problemBuilder.getObject()
                .title("generic-error")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
        log.error("Unknown error %s (%s): %s".formatted(ex.getClass().getCanonicalName(), ex.getMessage(), problem), ex);
        return problem.toResponse();
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Problem> handleException(MethodArgumentNotValidException ex) {
        List<Error> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(f -> new Error(f.getField(), f.getDefaultMessage()))
                .toList();
        var problem = problemBuilder.getObject()
                .title("invalid-data")
                .status(HttpStatus.BAD_REQUEST.value())
                .errors(errors)
                .build();
        log.info("MethodArgumentNotValidException (%s): %s".formatted(ex.getMessage(), problem), ex);
        return problem.toResponse();
    }

}

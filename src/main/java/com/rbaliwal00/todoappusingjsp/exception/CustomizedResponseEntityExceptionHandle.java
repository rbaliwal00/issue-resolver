package com.rbaliwal00.todoappusingjsp.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandle extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleAllException(Exception ex, WebRequest request) throws Exception {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now().toString(),ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleUserNotException(Exception ex, WebRequest request) throws Exception {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now().toString(),ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(IllegalArgumentException.class)
//    public final ResponseEntity<ErrorDetails> handleInvalidInputException(Exception ex, WebRequest request) throws Exception {
//        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now().toString(),ex.getMessage(),request.getDescription(false));
//        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.BAD_REQUEST);
//    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now().toString()," " + ex.getFieldError().getField() + " " + ex.getFieldError().getDefaultMessage(), request.getDescription(false));

        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }
}

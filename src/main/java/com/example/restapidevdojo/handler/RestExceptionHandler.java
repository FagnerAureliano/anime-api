package com.example.restapidevdojo.handler;

import com.example.restapidevdojo.exceptions.BadRequestException;
import com.example.restapidevdojo.exceptions.BadRequestExceptionDetails;
import com.example.restapidevdojo.exceptions.ValidationExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handleBadRequestException(BadRequestException bre) {
        return new ResponseEntity<>(
                BadRequestExceptionDetails.builder()
                        .timestamp(Instant.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Bad Request Exception, check the documentation")
                        .details(bre.getMessage())
                        .developerMessage(bre.getClass().getName())
                        .build(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionDetails> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
       List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

       String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
       String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

        return new ResponseEntity<>(
                ValidationExceptionDetails.builder()
                        .timestamp(Instant.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Bad Request Exception, Invalid Fields")
                        .details("Check the field(s) error.")
                        .developerMessage(exception.getClass().getName())
                        .fields(fields)
                        .fieldsMessage(fieldsMessage)
                        .build(), HttpStatus.BAD_REQUEST);
    }
}

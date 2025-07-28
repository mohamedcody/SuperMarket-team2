package com.team2.supermarket.exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

@ControllerAdvice
public class CustomExceptionHandler {

    @Autowired
    MessageSource messageSource;


    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundHandler(ChangeSetPersister.NotFoundException ex , Locale locale) {
        if(locale == null ){
            locale = new Locale("en");
        }
        String message = ex.getMessage();
        try{
            message = messageSource.getMessage( ex.getMessage() , null , locale );
        }catch(Exception exception){
            System.out.println("message code not found");
        }
        ErrorResponse error = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentInvalidHandler (MethodArgumentNotValidException ex ){
        ErrorResponse errorresponse=
                new ErrorResponse(ex.getBindingResult().getAllErrors().
                        get(0).getDefaultMessage(),
                        HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<ErrorResponse>(errorresponse,HttpStatus.NOT_ACCEPTABLE);
    }



}
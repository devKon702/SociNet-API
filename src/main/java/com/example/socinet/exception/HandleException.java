package com.example.socinet.exception;

import com.example.socinet.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandleException {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex){
        Response response = Response.builder()
                .isSuccess(false)
                .message(ex.getMessage())
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}

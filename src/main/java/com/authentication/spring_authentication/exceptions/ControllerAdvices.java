package com.authentication.spring_authentication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvices extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> emailAlreadyExistsHandler(EmailAlreadyExistsException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists.");
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<String> usernameAlreadyExistsHandler(UsernameAlreadyExistsException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists.");
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<String> roleNotFoundHandler(RoleNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role not found.");
    }


}

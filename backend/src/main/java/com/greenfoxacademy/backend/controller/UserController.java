package com.greenfoxacademy.backend.controller;

import com.greenfoxacademy.backend.dtos.RegisterUserDto;
import com.greenfoxacademy.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * REST controller where endpoints are handled.
 */
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
public class UserController {
    private final UserService userService;

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Validated @RequestBody RegisterUserDto registerUserDto) {
        if (!userService.emailValidation(registerUserDto.getEmail())) {
            return ResponseEntity.badRequest().body("Email is not valid!");
        } else if (userService.existsByEmail(registerUserDto.getEmail())) {
            return ResponseEntity.badRequest().body("Email is already exist!");
        }
        userService.register(registerUserDto);
        return ResponseEntity.ok().build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}


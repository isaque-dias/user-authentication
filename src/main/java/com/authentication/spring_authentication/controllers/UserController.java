package com.authentication.spring_authentication.controllers;

import com.authentication.spring_authentication.dto.request.UserRequest;
import com.authentication.spring_authentication.dto.response.UserResponse;
import com.authentication.spring_authentication.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRequest dto){
        UserResponse response = userService.registerUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<UserResponse> getProfile(){
        UserResponse response = userService.getProfile();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<UserResponse> update(@RequestBody @Valid UserRequest dto){
        UserResponse response = userService.updateProfile(dto);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping
    public ResponseEntity<Void> deleteProfile(){
        userService.deleteProfile();
        return ResponseEntity.noContent().build();
    }
}

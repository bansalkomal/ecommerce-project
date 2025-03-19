package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.UserRegistrationDTO;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Transactional
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserRegistrationDTO dto) {
        return new ResponseEntity<>(userService.registerUser(dto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String email, @RequestParam String password) {
        User user = userService.loginUser(email, password);
//        if (user != null) {
//            return ResponseEntity.ok(user);
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials !!");
        if (user != null) {
            ApiResponse<User> response = new ApiResponse<>(HttpStatus.OK.value(), "Login successful", user);
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<Void> response = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "Invalid Credentials !!", null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserProfile(@PathVariable Long id) {
        User user = userService.getUserProfile(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUserProfile(@RequestBody User user) {
        return ResponseEntity.ok(userService.updateUserProfile(user));
    }
}


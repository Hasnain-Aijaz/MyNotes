package com.notes.notesapp.controller;

import com.notes.notesapp.dto.RegisterRequest;
import com.notes.notesapp.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String registerUser (@RequestBody RegisterRequest request) {
        authService.register(request);
        return "User added successfully";
    }

}

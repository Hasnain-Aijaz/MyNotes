package com.notes.notesapp.service;

import com.notes.notesapp.dto.RegisterRequest;
import com.notes.notesapp.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.notes.notesapp.entity.User;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.CONFLICT;

@Service
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(CONFLICT, "Email already exists");
        }
            User user = new User();
            user.setEmail(request.getEmail());
            user.setPasswordHash(request.getPassword());
            user.setUsername(request.getUsername());
            userRepository.save(user);
    }
}

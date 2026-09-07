package com.notes.notesapp.service;

import com.notes.notesapp.dto.Login;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.notes.notesapp.dto.RegisterRequest;
import com.notes.notesapp.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.notes.notesapp.entity.User;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CONFLICT;

@Service
public class AuthService {
    private final UserRepository userRepository;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(CONFLICT, "Email already exists");
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPasswordHash(hashedPassword);
        user.setUsername(request.getUsername());
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);
    }

    public String login(Login login) {
        Optional<User> userExists = userRepository.findByEmail(login.getEmail());
        if (userExists.isEmpty()) {
            return "User does not exist";
        }
        User user = userExists.get();
        if(!passwordEncoder.matches(login.getPassword(), user.getPasswordHash())) {
            return "Wrong credentials";
        }

        return "Login successful";
    }
}

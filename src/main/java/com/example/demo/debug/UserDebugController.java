package com.example.demo.debug;


import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class UserDebugController {

    private final UserRepository userRepository;

    public UserDebugController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/all-users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
package com.example.demo.event;

public record UserRegisteredEvent(String username, String email, String role) {
}

package com.example.demo.dto;

import jakarta.validation.constraints.*;

public class PersonRequest {

    @NotBlank(message = "Imię nie może być puste")
    private String name;

    @Min(value = 0, message = "Wiek nie może być ujemny")
    @Max(value = 120, message = "Wiek nie może przekraczać 120 lat")
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

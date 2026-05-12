package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PersonResponse {

    private String name;

    public String getName() {
        return name;
    }

    public PersonResponse(String name, int age) {
        this.name = name;
        this.age = age;
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

    private int age;
}

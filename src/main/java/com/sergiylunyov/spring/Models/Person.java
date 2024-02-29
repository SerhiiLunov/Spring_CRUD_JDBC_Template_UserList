package com.sergiylunyov.spring.Models;

import com.sergiylunyov.spring.Validation.StartsWithUppercase;

import javax.validation.constraints.*;

public class Person {
    private int id;
    @NotBlank(message = "Name should not be empty")
    @Size(min = 2,max = 30, message = "Name should not be between 2 and 30 characters")
    @StartsWithUppercase(message = "Name should not be start with an uppercase letter")
    private String name;

    @Min(value = 0, message = "Age should not be greater then 0")
    @Max(value = 150, message = "Age should not be less then 150")
    private int age;

    @NotBlank(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;

    public Person(int id, String name, int age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public Person() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

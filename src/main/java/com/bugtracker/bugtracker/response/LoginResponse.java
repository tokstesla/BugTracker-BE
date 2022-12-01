package com.bugtracker.bugtracker.response;

import lombok.Data;

@Data
public class LoginResponse {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String message;

    public LoginResponse(int id, String firstName, String lastName, String email, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
    }

    public LoginResponse(String message) {
        this.message = message;
    }
}

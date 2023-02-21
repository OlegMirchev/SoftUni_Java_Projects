package com.example.LikeBook.Model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class LoginUserDto {

    @NotEmpty
    @Size(min = 3, max = 20, message = "Username error message.")
    private String username;

    @NotEmpty
    @Size(min = 3, max = 20, message = "Password error message.")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

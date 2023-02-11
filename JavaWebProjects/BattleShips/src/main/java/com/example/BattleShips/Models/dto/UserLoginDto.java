package com.example.BattleShips.Models.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UserLoginDto {

    @NotEmpty
    @Size(min = 3, max = 20, message = "Username length must be between 3 and 10 characters.")
    private String username;

    @NotEmpty
    @Size(min = 4, message = "Password length must be more than 3 characters.")
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

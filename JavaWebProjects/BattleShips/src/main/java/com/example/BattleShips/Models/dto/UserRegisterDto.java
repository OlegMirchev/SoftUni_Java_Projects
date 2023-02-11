package com.example.BattleShips.Models.dto;

import com.example.BattleShips.Validations.PasswordMatch;
import com.example.BattleShips.Validations.UniqueEmail;
import com.example.BattleShips.Validations.UniqueUsername;
import jakarta.validation.constraints.*;

@PasswordMatch(firstPass = "password", secondPass = "confirmPassword", message = "Passwords do not match")

public class UserRegisterDto {

    @NotEmpty
    @Size(min = 3, max = 20, message = "Username length must be between 3 and 20 characters.")
    @UniqueUsername(message = "Username is already exists")
    private String username;

    @NotEmpty(message = "Enter valid full name.")
    @Size(min = 5, max = 20, message = "Full name length must be between 5 and 20 characters.")
    private String fullName;

    @NotEmpty
    @Email(message = "Enter valid email address.")
    @UniqueEmail(message = "Email is already exists")
    private String email;

    @Size(min = 4, message = "Password length must be more than 3 characters long.")
    private String password;

    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}

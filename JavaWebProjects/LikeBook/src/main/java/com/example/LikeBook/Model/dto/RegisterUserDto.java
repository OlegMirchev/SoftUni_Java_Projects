package com.example.LikeBook.Model.dto;

import com.example.LikeBook.Validations.PasswordMatch;
import com.example.LikeBook.Validations.UniqueEmail;
import com.example.LikeBook.Validations.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@PasswordMatch(firstPass = "password", secondPass = "confirmPassword", message = "Passwords do not match.")

public class RegisterUserDto {

    @NotEmpty
    @Size(min = 3, max = 20, message = "Username error message.")
    @UniqueUsername(message = "Username is already exists.")
    private String username;

    @NotEmpty
    @Email(message = "Email error message.")
    @UniqueEmail(message = "Email is already exists.")
    private String email;

    @NotEmpty
    @Size(min = 3, max = 20, message = "Password error message.")
    private String password;

    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

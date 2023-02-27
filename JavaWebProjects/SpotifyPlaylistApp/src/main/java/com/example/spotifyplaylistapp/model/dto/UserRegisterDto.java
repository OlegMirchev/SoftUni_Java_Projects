package com.example.spotifyplaylistapp.model.dto;

import com.example.spotifyplaylistapp.Validations.PasswordMatch;
import com.example.spotifyplaylistapp.Validations.UniqueEmail;
import com.example.spotifyplaylistapp.Validations.UniqueUsername;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@PasswordMatch(firstPass = "password", secondPass = "confirmPassword", message = "Passwords do not match.")

public class UserRegisterDto {

    @NotEmpty
    @Size(min = 3, max = 20, message = "Username length must be between 3 and 20 characters.")
    @UniqueUsername(message = "Username is already exists.")
    private String username;

    @NotEmpty
    @Email(message = "Email error message.")
    @UniqueEmail(message = "Email is already exists.")
    private String email;

    @NotEmpty
    @Size(min = 3, max = 20, message = "Password length must be between 3 and 20 characters.")
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

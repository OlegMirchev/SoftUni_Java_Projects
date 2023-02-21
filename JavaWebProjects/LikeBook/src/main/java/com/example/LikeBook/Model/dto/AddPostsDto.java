package com.example.LikeBook.Model.dto;

import jakarta.validation.constraints.Size;

public class AddPostsDto {

    @Size(min = 2, max = 50, message = "Content length must be between 2 and 50 characters.")
    private String content;

    @Size(min = 1, message = "You must select a mood.")
    private String mood;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }
}

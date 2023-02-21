package com.example.LikeBook.Model.dto;

import com.example.LikeBook.Model.entities.Mood;
import com.example.LikeBook.Model.entities.User;

import java.util.Set;

public class PostOtherDto {

    private long id;

    private String userUsername;

    private String content;

    private Mood mood;

    private Set<User> likes;

    public PostOtherDto() {

    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Mood getMood() {
        return mood;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public Set<User> getLikes() {
        return likes;
    }

    public void setLikes(Set<User> likes) {
        this.likes = likes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

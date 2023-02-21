package com.example.LikeBook.Model.dto;

import com.example.LikeBook.Model.entities.Mood;
import com.example.LikeBook.Model.entities.User;
import com.example.LikeBook.Utils.Enum.MoodType;

import java.util.Set;

public class PostUserDto {

    private long id;

    private String content;

    private Mood mood;

    private Set<User> likes;

    public PostUserDto() {

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

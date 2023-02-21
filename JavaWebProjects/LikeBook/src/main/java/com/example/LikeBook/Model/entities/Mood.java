package com.example.LikeBook.Model.entities;

import com.example.LikeBook.Utils.Enum.MoodType;
import jakarta.persistence.*;

@Entity
@Table(name = "moods")
public class Mood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private MoodType name;

    @Column(columnDefinition = "TEXT")
    private String description;

    public Mood() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MoodType getName() {
        return name;
    }

    public void setName(MoodType name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

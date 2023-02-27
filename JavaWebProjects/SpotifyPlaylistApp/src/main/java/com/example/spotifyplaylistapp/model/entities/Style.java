package com.example.spotifyplaylistapp.model.entities;

import com.example.spotifyplaylistapp.util.Enum.StyleType;

import javax.persistence.*;

@Entity
@Table(name = "styles")
public class Style {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private StyleType name;

    @Column(columnDefinition = "TEXT")
    private String description;

    public Style() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public StyleType getName() {
        return name;
    }

    public void setName(StyleType name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

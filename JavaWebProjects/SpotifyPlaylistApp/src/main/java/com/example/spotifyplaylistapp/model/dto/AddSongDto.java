package com.example.spotifyplaylistapp.model.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class AddSongDto {

    @Size(min = 3, max = 20, message = "Performer name length must be between 3 and 20 characters (inclusive of 3 and 20).")
    private String performer;

    @Size(min = 2, max = 20, message = "Title length must be between 2 and 20 characters (inclusive of 2 and 20).")
    private String title;

    @PastOrPresent(message = "The Date that cannot be in the future.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    @Positive(message = "The duration (in seconds) must be a positive number.")
    private int duration;

    @Size(min = 1, message = "You must select a style.")
    private String style;

    public String getPerformer() {
        return performer;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }
}

package com.example.football.models.dto;

import com.example.football.util.PositionPlayer;

public class ExportPlayersDto {

    private String firstName;

    private String lastName;

    private PositionPlayer position;

    private String teamName;

    private String stadiumName;

    public ExportPlayersDto() {

    }

    public ExportPlayersDto(String firstName, String lastName, PositionPlayer position, String teamName, String stadiumName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.teamName = teamName;
        this.stadiumName = stadiumName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public PositionPlayer getPosition() {
        return position;
    }

    public void setPosition(PositionPlayer position) {
        this.position = position;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getStadiumName() {
        return stadiumName;
    }

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }
}

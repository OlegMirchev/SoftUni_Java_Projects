package com.example.football.models.dto;

import com.example.football.util.LocalDateAdapter;
import com.example.football.util.PositionPlayer;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerDto {

    @XmlElement(name = "first-name")
    @Size(min = 2)
    private String firstName;

    @XmlElement(name = "last-name")
    @Size(min = 2)
    private String lastName;

    @XmlElement
    @Email
    private String email;

    @XmlElement(name = "birth-date")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate birthDate;

    @XmlElement
    private PositionPlayer position;

    @XmlElement(name = "town")
    private TownPlayerDto town;

    @XmlElement(name = "team")
    private TeamPlayerDto team;

    @XmlElement(name = "stat")
    private StatPlayerDto stat;

    public PlayerDto() {

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public PositionPlayer getPosition() {
        return position;
    }

    public TownPlayerDto getTown() {
        return town;
    }

    public TeamPlayerDto getTeam() {
        return team;
    }

    public StatPlayerDto getStat() {
        return stat;
    }
}

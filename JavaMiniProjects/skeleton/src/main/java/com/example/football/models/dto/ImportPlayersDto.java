package com.example.football.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "players")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportPlayersDto {

    @XmlElement(name = "player")
    private List<PlayerDto> players;

    public ImportPlayersDto() {

    }

    public List<PlayerDto> getPlayers() {
        return players;
    }
}

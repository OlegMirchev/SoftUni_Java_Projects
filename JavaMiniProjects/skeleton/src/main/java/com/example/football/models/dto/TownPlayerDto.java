package com.example.football.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class TownPlayerDto {

    @XmlElement
    private String name;

    public TownPlayerDto() {

    }

    public String getName() {
        return name;
    }
}

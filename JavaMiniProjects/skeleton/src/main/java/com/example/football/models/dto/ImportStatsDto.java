package com.example.football.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "stats")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportStatsDto {

    @XmlElement(name = "stat")
    private List<StatDto> stats;

    public ImportStatsDto() {

    }

    public List<StatDto> getStats() {
        return stats;
    }
}

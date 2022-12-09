package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class OfferAgentDto {

    @XmlElement
    private String name;

    public OfferAgentDto() {

    }

    public String getName() {
        return name;
    }
}

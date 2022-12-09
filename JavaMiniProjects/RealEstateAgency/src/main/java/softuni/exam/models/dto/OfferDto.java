package softuni.exam.models.dto;

import softuni.exam.util.LocalDateAdapter;

import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
public class OfferDto {

    @XmlElement
    @Positive
    private BigDecimal price;

    @XmlElement(name = "agent")
    private OfferAgentDto agent;

    @XmlElement(name = "apartment")
    private OfferApartmentDto apartment;

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate publishedOn;

    public OfferDto() {

    }

    public BigDecimal getPrice() {
        return price;
    }

    public OfferAgentDto getAgent() {
        return agent;
    }

    public OfferApartmentDto getApartment() {
        return apartment;
    }

    public LocalDate getPublishedOn() {
        return publishedOn;
    }
}

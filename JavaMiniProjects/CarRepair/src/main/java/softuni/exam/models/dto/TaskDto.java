package softuni.exam.models.dto;

import softuni.exam.util.LocalDateTimeAdapter;

import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@XmlAccessorType(XmlAccessType.FIELD)
public class TaskDto {

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime date;

    @XmlElement
    @Positive
    private BigDecimal price;

    @XmlElement(name = "car")
    private TaskCarDto car;

    @XmlElement(name = "mechanic")
    private TaskMechanicDto mechanic;

    @XmlElement(name = "part")
    private TaskPartDto part;

    public TaskDto() {

    }

    public LocalDateTime getDate() {
        return date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public TaskCarDto getCar() {
        return car;
    }

    public TaskMechanicDto getMechanic() {
        return mechanic;
    }

    public TaskPartDto getPart() {
        return part;
    }
}

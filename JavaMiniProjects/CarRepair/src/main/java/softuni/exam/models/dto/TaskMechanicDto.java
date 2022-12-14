package softuni.exam.models.dto;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class TaskMechanicDto {

    @XmlElement
    @Size(min = 2)
    private String firstName;

    public TaskMechanicDto() {

    }

    public String getFirstName() {
        return firstName;
    }
}

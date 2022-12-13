package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class TaskPartDto {

    @XmlElement
    private long id;

    public TaskPartDto() {

    }

    public long getId() {
        return id;
    }
}

package exam.model.dto;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ShopTownDto {

    @XmlElement
    @Size(min = 2)
    private String name;

    public ShopTownDto() {

    }

    public String getName() {
        return name;
    }
}

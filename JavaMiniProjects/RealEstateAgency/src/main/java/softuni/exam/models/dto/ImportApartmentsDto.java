package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "apartments")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportApartmentsDto {

    @XmlElement(name = "apartment")
    private List<ApartmentDto> apartments;

    public ImportApartmentsDto() {

    }

    public List<ApartmentDto> getApartments() {
        return apartments;
    }
}

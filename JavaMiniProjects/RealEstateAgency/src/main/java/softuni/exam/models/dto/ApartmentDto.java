package softuni.exam.models.dto;

import softuni.exam.util.ApartmentType;

import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ApartmentDto {

    @XmlElement
    private ApartmentType apartmentType;

    @XmlElement
    @Min(40)
    private double area;

    @XmlElement
    private String town;

    public ApartmentDto() {

    }

    public ApartmentType getApartmentType() {
        return apartmentType;
    }

    public double getArea() {
        return area;
    }

    public String getTown() {
        return town;
    }
}

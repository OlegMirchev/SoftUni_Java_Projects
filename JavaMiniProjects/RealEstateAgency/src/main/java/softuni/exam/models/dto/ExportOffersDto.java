package softuni.exam.models.dto;

import java.math.BigDecimal;

public class ExportOffersDto {

    private String fullName;

    private long id;

    private double area;

    private String townName;

    private BigDecimal price;

    public ExportOffersDto() {

    }

    public ExportOffersDto(String firstName, String lastName, long id, double area, String townName, BigDecimal price) {
        this.fullName = firstName + " " + lastName;
        this.id = id;
        this.area = area;
        this.townName = townName;
        this.price = price;
}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

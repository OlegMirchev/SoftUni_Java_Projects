package softuni.exam.models.dto;

import java.math.BigDecimal;

public class ExportTasksDto {

    private String carMake;

    private String carModel;

    private int kilometers;

    private String firstName;

    private String lastName;

    private long id;

    private double engine;

    private BigDecimal price;

    public ExportTasksDto() {

    }

    public ExportTasksDto(String carMake, String carModel, int kilometers, String firstName, String lastName, long id, double engine, BigDecimal price) {
        this.carMake = carMake;
        this.carModel = carModel;
        this.kilometers = kilometers;
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.engine = engine;
        this.price = price;
    }

    public String getCarMake() {
        return carMake;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public int getKilometers() {
        return kilometers;
    }

    public void setKilometers(int kilometers) {
        this.kilometers = kilometers;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getEngine() {
        return engine;
    }

    public void setEngine(double engine) {
        this.engine = engine;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

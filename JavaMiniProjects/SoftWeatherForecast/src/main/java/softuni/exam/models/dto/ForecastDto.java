package softuni.exam.models.dto;

import org.hibernate.validator.constraints.Range;
import softuni.exam.util.DayOfWeek;
import softuni.exam.util.LocalTimeAdapter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalTime;

@XmlAccessorType(XmlAccessType.FIELD)
public class ForecastDto {

    @XmlElement(name = "day_of_week")
    @NotNull
    private DayOfWeek dayOfWeek;

    @XmlElement(name = "max_temperature")
    @Min(-20)
    @Max(60)
    private float maxTemperature;

    @XmlElement(name = "min_temperature")
    @Min(-50)
    @Max(40)
    private float minTemperature;

    @XmlElement
    @NotNull
    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    private LocalTime sunrise;

    @XmlElement
    @NotNull
    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    private LocalTime sunset;

    @XmlElement
    private long city;

    public ForecastDto() {

    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public float getMaxTemperature() {
        return maxTemperature;
    }

    public float getMinTemperature() {
        return minTemperature;
    }

    public LocalTime getSunrise() {
        return sunrise;
    }

    public LocalTime getSunset() {
        return sunset;
    }

    public long getCity() {
        return city;
    }
}

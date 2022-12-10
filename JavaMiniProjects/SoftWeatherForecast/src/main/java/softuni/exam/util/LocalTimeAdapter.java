package softuni.exam.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeAdapter extends XmlAdapter<String, LocalTime> {
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public LocalTime unmarshal(String time) throws Exception {
        return LocalTime.parse(time, TIME_FORMAT);
    }

    @Override
    public String marshal(LocalTime localTime) throws Exception {
        return localTime.format(TIME_FORMAT);
    }
}

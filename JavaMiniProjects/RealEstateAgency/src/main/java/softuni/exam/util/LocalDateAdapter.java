package softuni.exam.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public LocalDate unmarshal(String date) throws Exception {
        return LocalDate.parse(date, DATE_FORMAT);
    }

    @Override
    public String marshal(LocalDate localDate) throws Exception {
        return localDate.format(DATE_FORMAT);
    }
}

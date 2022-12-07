package com.example.football.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    private static final DateTimeFormatter FORMAT_STRING = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public LocalDate unmarshal(String s) throws Exception {
        return LocalDate.parse(s, FORMAT_STRING);
    }

    @Override
    public String marshal(LocalDate localDate) throws Exception {
        return localDate.format(FORMAT_STRING);
    }
}

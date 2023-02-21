package com.example.LikeBook.Utils.Enum;

public enum MoodType {

    HAPPY ("Happy"),
    SAD ("Sad"),
    INSPIRED ("Inspired");

    private final String value;

    MoodType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

package com.example.spotifyplaylistapp.util.Enum;

public enum StyleType {
    POP ("Pop"),
    ROCK ("Rock"),
    JAZZ ("Jazz");

    private final String value;

    StyleType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

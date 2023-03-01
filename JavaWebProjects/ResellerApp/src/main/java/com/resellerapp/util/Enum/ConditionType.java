package com.resellerapp.util.Enum;

public enum ConditionType {

    EXCELLENT ("Excellent"),
    GOOD ("Good"),
    ACCEPTABLE ("Acceptable");

    private final String value;

    ConditionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

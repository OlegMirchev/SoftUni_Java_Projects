package com.resellerapp.model.dto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class AddOfferDto {

    @Size(min = 2, max = 50, message = "Description length must be between 2 and 50 characters (inclusive of 2 and 50).")
    private String description;

    @Positive(message = "The price must be a positive number.")
    private float price;

    @Size(min = 1, message = "You must select a condition.")
    private String condition;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}

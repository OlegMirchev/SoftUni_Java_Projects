package com.resellerapp.model.dto;

import com.resellerapp.model.entities.Condition;

public class OfferUserDto {

    private long id;

    private String description;

    private float price;

    private Condition condition;

    public OfferUserDto() {

    }

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

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

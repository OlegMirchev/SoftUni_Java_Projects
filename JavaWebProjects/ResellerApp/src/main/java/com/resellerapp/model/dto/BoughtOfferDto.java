package com.resellerapp.model.dto;

public class BoughtOfferDto {

    private String description;

    private float price;

    public BoughtOfferDto() {

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
}

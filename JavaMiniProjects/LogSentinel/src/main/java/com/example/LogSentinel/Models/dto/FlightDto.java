package com.example.LogSentinel.Models.dto;

import com.example.LogSentinel.Utils.DifferentType;

import java.time.LocalDateTime;

public class FlightDto {

    private String orderNumber;

    private String amount;

    private LocalDateTime startDate;

    private DifferentType type;


    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public DifferentType getType() {
        return type;
    }

    public void setType(DifferentType type) {
        this.type = type;
    }
}

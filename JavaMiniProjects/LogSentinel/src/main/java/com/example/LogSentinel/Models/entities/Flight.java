package com.example.LogSentinel.Models.entities;

import com.example.LogSentinel.Utils.DifferentType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "flights")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "order_number", nullable = false)
    private int orderNumber;

    @Column(nullable = false)
    private String amount;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DifferentType type;

    public Flight() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
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

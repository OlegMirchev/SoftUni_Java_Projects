package com.example.BattleShips.Models.dto;

import com.example.BattleShips.Utils.Enum.CategoryType;
import com.example.BattleShips.Validations.UniqueNameShip;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class AddShipDto {

    @Size(min = 2, max = 10, message = "Name must be between 2 and 10 characters.")
    @UniqueNameShip(message = "Name is already exists.")
    private String name;

    @Positive(message = "The power must be positive.")
    private int power;

    @Positive(message = "The power must be positive.")
    private int health;

    @PastOrPresent(message = "Created date cannot be in the future.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate created;

    @PositiveOrZero(message = "You must select the category.")
    private int category = -1;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}

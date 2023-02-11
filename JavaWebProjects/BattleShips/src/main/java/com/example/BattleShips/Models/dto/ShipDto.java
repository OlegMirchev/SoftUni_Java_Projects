package com.example.BattleShips.Models.dto;

public class ShipDto {

    private long id;

    private String name;

    private long power;

    private long health;

    public ShipDto() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPower() {
        return power;
    }

    public void setPower(long power) {
        this.power = power;
    }

    public long getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}

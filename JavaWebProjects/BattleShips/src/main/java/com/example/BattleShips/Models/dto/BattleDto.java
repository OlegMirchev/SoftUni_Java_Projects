package com.example.BattleShips.Models.dto;

import jakarta.validation.constraints.Positive;

public class BattleDto {

    @Positive
    private long attackerId;

    @Positive
    private long defenderId;

    public long getAttackerId() {
        return attackerId;
    }

    public void setAttackerId(long attackerId) {
        this.attackerId = attackerId;
    }

    public long getDefenderId() {
        return defenderId;
    }

    public void setDefenderId(long defenderId) {
        this.defenderId = defenderId;
    }
}

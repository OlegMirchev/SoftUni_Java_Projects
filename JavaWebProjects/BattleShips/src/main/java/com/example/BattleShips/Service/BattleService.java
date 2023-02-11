package com.example.BattleShips.Service;

import com.example.BattleShips.Models.dto.BattleDto;
import com.example.BattleShips.Models.entities.Ship;
import com.example.BattleShips.Repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BattleService {

    private ShipRepository shipRepository;

    @Autowired
    public BattleService(ShipRepository shipRepository) {
        this.shipRepository = shipRepository;
    }

    public boolean fightShips(BattleDto battleDto) {
        Optional<Ship> attackerOpt = this.shipRepository.findById(battleDto.getAttackerId());
        Optional<Ship> defenderOpt = this.shipRepository.findById(battleDto.getDefenderId());

        if (attackerOpt.isEmpty() || defenderOpt.isEmpty()) {
            return false;
        }

        Ship attacker = attackerOpt.get();
        Ship defender = defenderOpt.get();

        long defenderHealth = defender.getHealth() - attacker.getPower();

        if (defenderHealth <= 0) {
            this.shipRepository.delete(defender);
        }else {
            defender.setHealth(defenderHealth);
            this.shipRepository.save(defender);
        }

        return true;
    }
}

package com.example.BattleShips.Validations;

import com.example.BattleShips.Repository.ShipRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.internal.build.AllowPrintStacktrace;

public class UniqueNameShipValidator implements ConstraintValidator<UniqueNameShip, String> {

    private ShipRepository shipRepository;

    @AllowPrintStacktrace
    public UniqueNameShipValidator(ShipRepository shipRepository) {
        this.shipRepository = shipRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return this.shipRepository.findByName(value).isEmpty();
    }
}

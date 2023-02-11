package com.example.BattleShips.Repository;

import com.example.BattleShips.Models.dto.ShipDto;
import com.example.BattleShips.Models.entities.Ship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShipRepository extends JpaRepository<Ship, Long> {

    Optional<Ship> findByName(String value);

    List<Ship> findByUserId(Long userId);

    List<Ship> findByUserIdNot(Long otherId);

    List<Ship> findByOrderByNameDescHealthAscPowerAsc();
}

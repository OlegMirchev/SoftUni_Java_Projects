package com.example.football.repository;

import com.example.football.models.dto.ExportPlayersDto;
import com.example.football.models.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findByFirstNameAndLastName(String firstName, String lastName);

    @Query("SELECT new com.example.football.models.dto.ExportPlayersDto(p.firstName, p.lastName, p.position, t.name AS teamName, t.stadiumName AS stadiumName)" +
            " FROM Player AS p" +
            " JOIN p.team AS t" +
            " JOIN p.stat AS s" +
            " WHERE p.birthDate BETWEEN '1995-01-01' AND '2003-01-01'" +
            " ORDER BY s.shooting DESC, s.passing DESC, s.endurance DESC, p.lastName ASC")
    List<ExportPlayersDto> findBestPlayersFromFootballTeam();
}

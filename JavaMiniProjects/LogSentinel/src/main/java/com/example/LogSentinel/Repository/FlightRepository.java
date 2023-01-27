package com.example.LogSentinel.Repository;

import com.example.LogSentinel.Models.dto.FlightStatisticsDto;
import com.example.LogSentinel.Models.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("SELECT f FROM Flight AS f")
    List<Flight> findAllFlights();

    @Modifying
    @Transactional
    @Query("UPDATE Flight AS f" +
            " SET f.amount = '30.06'" +
            " WHERE f.id = :flightId")
    Flight findByIdFlightAndChangeHisAmount(@Param("flightId") long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Flight AS f" +
            " WHERE f.type = 1")
    int findAllFightsAndCountOfDeleteThem();

    @Query("SELECT new com.example.LogSentinel.Models.dto.FlightStatisticsDto(SUM(TO_NUMBER(f.amount)) AS sum," +
            " AVG(CAST(f.amount AS double)) AS avg," +
            " MAX(CAST(f.amount AS double)) AS max, MIN(CAST(f.amount AS double)) AS min, COUNT(f.id) AS count)" +
            " FROM Flight AS f")
    FlightStatisticsDto findStatisticsOfFlights();
}

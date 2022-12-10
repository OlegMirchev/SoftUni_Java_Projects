package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softuni.exam.models.dto.ExportForecastsDto;
import softuni.exam.models.entity.Forecast;
import softuni.exam.util.DayOfWeek;

import java.util.List;
import java.util.Optional;

@Repository
public interface ForecastRepository extends JpaRepository<Forecast, Long> {

    @Query("SELECT f FROM Forecast AS f" +
            " JOIN f.city AS c" +
            " WHERE c.id = :id AND f.dayOfWeek = :dayOfWeek")
    Optional<Forecast> findForecastsWith(@Param("id") long city, DayOfWeek dayOfWeek);

    @Query("SELECT new softuni.exam.models.dto.ExportForecastsDto(c.cityName, f.minTemperature, f.maxTemperature, f.sunrise, f.sunset)" +
            " FROM Forecast AS f" +
            " JOIN f.city AS c" +
            " WHERE f.dayOfWeek = 2 AND c.population < 150000" +
            " ORDER BY f.maxTemperature DESC, f.id ASC")
    List<ExportForecastsDto> findSundayForecasts();
}

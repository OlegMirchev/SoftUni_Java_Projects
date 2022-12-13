package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.dto.ExportTasksDto;
import softuni.exam.models.entity.Task;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task AS t" +
            " JOIN t.car AS c" +
            " JOIN t.mechanic AS m" +
            " JOIN t.part AS p" +
            " WHERE c.id = :carId AND m.firstName LIKE :firstName AND p.id = :partId")
    Optional<Task> findByCarMechanicPart(long carId, String firstName, long partId);

    @Query("SELECT new softuni.exam.models.dto.ExportTasksDto(c.carMake, c.carModel, c.kilometers, m.firstName, m.lastName, t.id," +
            " c.engine, t.price)" +
            " FROM Task AS t" +
            " JOIN t.car AS c" +
            " JOIN t.mechanic AS m" +
            " WHERE c.carType = 1" +
            " ORDER BY t.price DESC")
    List<ExportTasksDto> findAllBestTasksWithTypeCoupe();
}

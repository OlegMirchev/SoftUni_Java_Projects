package exam.repository;

import exam.model.dto.ExportLaptopsDto;
import exam.model.entities.Laptop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LaptopRepository extends JpaRepository<Laptop, Long> {

    @Query("SELECT l FROM Laptop AS l" +
            " JOIN l.shop AS s" +
            " WHERE s.name LIKE :name")
    Optional<Laptop> findShopName(String name);

    @Query("SELECT new exam.model.dto.ExportLaptopsDto(l.macAddress, l.cpuSpeed, l.ram, l.storage, l.price, s.name AS shopName, t.name AS townName)" +
            " FROM Laptop AS l" +
            " JOIN l.shop AS s" +
            " JOIN s.town AS t" +
            " ORDER BY l.cpuSpeed DESC, l.ram DESC, l.storage DESC, l.macAddress ASC")
    List<ExportLaptopsDto> findBestLaptops();
}

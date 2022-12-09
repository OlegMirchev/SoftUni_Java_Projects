package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Apartment;
import softuni.exam.util.ApartmentType;

import java.util.Optional;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

    @Query("SELECT a FROM Apartment AS a" +
            " JOIN a.town AS t" +
            " WHERE a.apartmentType LIKE :apartmentType AND a.area = :area AND t.townName LIKE :townName")
    Optional<Apartment> findApartmentTypeAndAreaAndTown(ApartmentType apartmentType, double area, String townName);
}

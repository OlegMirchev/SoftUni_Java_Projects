package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.dto.ExportOffersDto;
import softuni.exam.models.entity.Offer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    @Query("SELECT o FROM Offer AS o" +
            " JOIN o.agent AS ag" +
            " JOIN o.apartment AS ap" +
            " WHERE o.price = :price AND ag.firstName LIKE :name AND ap.id = :id AND o.publishedOn LIKE :publishedOn")
    Optional<Offer> findOfferWithPriceAndAgentAndApartmentAndDate(BigDecimal price, String name, long id, LocalDate publishedOn);

    @Query("SELECT new softuni.exam.models.dto.ExportOffersDto(ag.firstName, ag.lastName, o.id, ap.area," +
            " t.townName, o.price)" +
            " FROM Offer AS o" +
            " JOIN o.agent AS ag" +
            " JOIN o.apartment AS ap" +
            " JOIN ap.town AS t" +
            " WHERE ap.apartmentType = 1" +
            " ORDER BY ap.area DESC, o.price ASC")
    List<ExportOffersDto> findBestOffersFromDateBase();
}

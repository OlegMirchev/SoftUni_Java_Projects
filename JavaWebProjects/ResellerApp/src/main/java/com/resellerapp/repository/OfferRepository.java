package com.resellerapp.repository;

import com.resellerapp.model.entities.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    List<Offer> findAllByUserId(long id);

    List<Offer> findPostsByUserIdNot(long id);
}

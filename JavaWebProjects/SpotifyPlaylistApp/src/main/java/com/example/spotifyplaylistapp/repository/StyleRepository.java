package com.example.spotifyplaylistapp.repository;

import com.example.spotifyplaylistapp.model.entities.Style;
import com.example.spotifyplaylistapp.util.Enum.StyleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StyleRepository extends JpaRepository<Style, Long> {

    Optional<Style> findByName(StyleType valueOf);
}

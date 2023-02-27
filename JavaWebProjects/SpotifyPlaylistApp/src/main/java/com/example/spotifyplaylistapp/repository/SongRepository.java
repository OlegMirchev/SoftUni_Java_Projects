package com.example.spotifyplaylistapp.repository;

import com.example.spotifyplaylistapp.model.entities.Song;
import com.example.spotifyplaylistapp.util.Enum.StyleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

    List<Song> findByStyleName(StyleType style);
}

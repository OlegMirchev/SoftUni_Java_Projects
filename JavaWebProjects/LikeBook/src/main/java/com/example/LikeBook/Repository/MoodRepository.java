package com.example.LikeBook.Repository;

import com.example.LikeBook.Model.entities.Mood;
import com.example.LikeBook.Utils.Enum.MoodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MoodRepository extends JpaRepository<Mood, Long> {

    Optional<Mood> findByName(MoodType mood);
}

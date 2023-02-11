package com.example.BattleShips.Repository;

import com.example.BattleShips.Models.entities.Category;
import com.example.BattleShips.Utils.Enum.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(CategoryType name);
}

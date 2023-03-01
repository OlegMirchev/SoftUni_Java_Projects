package com.resellerapp.repository;

import com.resellerapp.model.entities.Condition;
import com.resellerapp.util.Enum.ConditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConditionRepository extends JpaRepository<Condition, Long> {

    Optional<Condition> findByName(ConditionType value);
}

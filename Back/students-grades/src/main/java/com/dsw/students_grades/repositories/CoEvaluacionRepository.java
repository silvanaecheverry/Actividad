package com.dsw.students_grades.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dsw.students_grades.entities.CoEvaluacionEntity;

@Repository
public interface CoEvaluacionRepository extends JpaRepository<CoEvaluacionEntity, Long> {
    //List<CoEvaluacionEntity> findByName(String name);
}


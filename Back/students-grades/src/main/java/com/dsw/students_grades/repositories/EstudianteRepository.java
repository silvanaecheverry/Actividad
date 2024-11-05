package com.dsw.students_grades.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dsw.students_grades.entities.EstudianteEntity;


/**
 * Interfaz que permite la persistencia de los estudiantes
 *
 * @author Jeronimo A. Pineda Cano
 *
 */
@Repository
public interface EstudianteRepository extends JpaRepository<EstudianteEntity, Long> {
    List<EstudianteEntity> findByLogin(String login);
}
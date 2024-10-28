package com.dsw.students_grades.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.diariomedico.entities.PacienteEntity;


/**
 * Interfaz que permite la persistencia de los estudiantes
 *
 * @author Jeronimo A. Pineda Cano
 *
 */
@Repository
public interface EstudianteRepository extends JpaRepository<EstudianteEntity, Long> {


package com.dsw.students_grades.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dsw.students_grades.entities.GrupoEntity;


@Repository
public interface GrupoRepository extends JpaRepository<GrupoEntity, Long> {
	//List<GrupoEntity> findByName(String name);

}

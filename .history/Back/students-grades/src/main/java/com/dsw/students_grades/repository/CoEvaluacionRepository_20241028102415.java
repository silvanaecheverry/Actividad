package com.dsw.students_grades.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoEvaulacionRepository extends JpaRepository<CoEvaulacion, Long> {
	List<CoEvaulacion> findByName(String name);
}

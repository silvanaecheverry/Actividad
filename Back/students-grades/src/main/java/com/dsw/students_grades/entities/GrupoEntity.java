package com.dsw.students_grades.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude; 

@Data
@Entity
public class GrupoEntity extends BaseEntity {
    private Integer numero;
    private String nombre;

    @PodamExclude
    @OneToMany(mappedBy = "grupo")
    private List<EstudianteEntity> estudiantes = new ArrayList<>();
}
package com.dsw.students_grades.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;

import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStringValue;

/**
 * Clase que representa un estudiante en la persistencia.
 *
 * @author Jeronimo A. Pineda Cano
 */

@Data
@Entity
public class EstudianteEntity extends BaseEntity {

    @PodamStringValue
    private String login;

    @PodamStringValue
    private String nombre;

    //Relaciones
    @PodamExclude
    @OneToMany(mappedBy = "estudianteCalificador", fetch = FetchType.LAZY)
    private List<CoEvaluacionEntity> evaluacionesHechas = new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "estudianteCalificado", fetch = FetchType.LAZY)
    private List<CoEvaluacionEntity> evaluacionesRecibidas = new ArrayList<>();

    @PodamExclude
    @ManyToOne
    private GrupoEntity grupo;
}

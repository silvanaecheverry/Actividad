package com.dsw.students_grades.entities;
import java.util.Date;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class CoEvaluacionEntity extends BaseEntity {

    private double calificacion;
    private String comentario;
    private Date fecha;
    
    @PodamExclude
    @ManyToOne
    private EstudianteEntity estudiante;
}
package com.dsw.students_grades.entities;
import java.util.Date;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import uk.co.jemos.podam.common.PodamDoubleValue;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;
import uk.co.jemos.podam.common.PodamStringValue;
import com.dsw.students_grades.podam.CustomDateStrategy;

@Data
@Entity
public class CoEvaluacionEntity extends BaseEntity {
    
    @PodamDoubleValue(minValue = 0.0, maxValue = 5.0)
    private Double calificacion;

    @PodamStringValue
    private String comentario;

    @Temporal(TemporalType.DATE)
    @PodamStrategyValue(CustomDateStrategy.class)
    private Date fecha;
    
    @PodamExclude
    @ManyToOne
    private EstudianteEntity estudianteCalificado;

    @PodamExclude
    @ManyToOne
    private EstudianteEntity estudianteCalificador;
}
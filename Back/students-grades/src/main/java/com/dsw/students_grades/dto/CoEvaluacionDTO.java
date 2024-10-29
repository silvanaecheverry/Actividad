package com.dsw.students_grades.dto;

import java.util.Date;

import lombok.Data;

/**
 * Clase que representa el DTO de una coevaluación
 *
 * @author Omar David Toledo Leguizamón
 */

@Data
public class CoEvaluacionDTO {
    private double calificacion;
    private String comentario;
    private Date fecha;

    private EstudianteDTO estudiante;
}

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
    private Double calificacion;
    private String comentario;
    private Date fecha;

    private EstudianteDTO estudianteCalificado;
    private EstudianteDTO estudianteCalificador;
}

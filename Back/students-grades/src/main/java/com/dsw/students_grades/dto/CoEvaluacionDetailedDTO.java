package com.dsw.students_grades.dto;

import lombok.Data;

/**
 * Clase que representa el DTO de una coevaluación
 *
 * @author Omar David Toledo Leguizamón
 */

@Data
public class CoEvaluacionDetailedDTO extends CoEvaluacionDTO{
    private EstudianteDTO estudianteCalificado;
    private EstudianteDTO estudianteCalificador;
}

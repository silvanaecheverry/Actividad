package com.dsw.students_grades.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Clase que representa el DTO detallado de un estudiante
 *
 * @author Omar David Toledo Leguizamón
 */

@Data
public class EstudianteDetailedDTO extends EstudianteDTO{
    List<CoEvaluacionDTO> evaluacionesRecibidas = new ArrayList<>();
    List<CoEvaluacionDTO> evaluacionesHechas = new ArrayList<>();
}

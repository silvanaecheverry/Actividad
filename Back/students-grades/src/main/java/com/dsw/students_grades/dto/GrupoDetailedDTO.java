package com.dsw.students_grades.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Clase que representa el DTO detallado de un grupo
 *
 * @author Omar David Toledo Leguizamón
 */

@Data
public class GrupoDetailedDTO extends GrupoDTO{
    private List<EstudianteDTO> estudiantes = new ArrayList<>();
}

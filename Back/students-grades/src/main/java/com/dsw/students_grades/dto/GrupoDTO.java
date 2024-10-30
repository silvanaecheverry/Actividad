package com.dsw.students_grades.dto;

import lombok.Data;

/**
 * Clase que representa el DTO de un grupo
 *
 * @author Omar David Toledo Leguizamón
 */

@Data
public class GrupoDTO {
    private Long id;
    private Integer numero;
    private String nombre;
}

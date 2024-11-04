package com.dsw.students_grades.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.dsw.students_grades.dto.EstudianteDTO;
import com.dsw.students_grades.dto.GrupoDetailedDTO;
import com.dsw.students_grades.entities.GrupoEntity;
import com.dsw.students_grades.entities.EstudianteEntity;
import com.dsw.students_grades.exceptions.EntityNotFoundException;
import com.dsw.students_grades.exceptions.IllegalOperationException;
import com.dsw.students_grades.services.GrupoEstudianteService;


@RestController
@RequestMapping("/grupos")
public class GrupoEstudianteController {

    @Autowired
    GrupoEstudianteService grupoEstudianteService;

    @Autowired
    private ModelMapper modelMapper;

    /** Obtiene los estudiantes de un grupo
     * 
     * @param grupoId
     * @return Lista de estudiantes mapeadaos al DTO
     * @throws EntityNotFoundException
     */
    @GetMapping(value = "{grupoId}/estudiantes")
    @ResponseStatus(code = HttpStatus.OK)
    public List<EstudianteDTO> getEstudiantes(@PathVariable Long grupoId) throws EntityNotFoundException {
        List<EstudianteEntity> list = grupoEstudianteService.getEstudiantes(grupoId);
        return modelMapper.map(list, new org.modelmapper.TypeToken<List<EstudianteDTO>>() {}.getType());
    }

    /** Obtiene un estudiante de un grupo
     * 
     * @param grupoId
     * @return Estudiante mapeado al DTO
     * @throws EntityNotFoundException
     * @throws IllegalOperationException 
     */

    @GetMapping(value = "{grupoId}/estudiantes/{estudianteId}")
    @ResponseStatus(code = HttpStatus.OK)
    public EstudianteDTO getEstudiante(@PathVariable Long grupoId, @PathVariable Long estudianteId) throws EntityNotFoundException, IllegalOperationException {
        EstudianteEntity e = grupoEstudianteService.getEstudiante(grupoId, estudianteId);
        return modelMapper.map(e, EstudianteDTO.class);
    }

    /** Añade estudiante a grupo
     * 
     * @param grupoId
     * @param estudianteId
     * @return Grupo detallado mapeado a DTO
     * @throws EntityNotFoundException
     */
    @PostMapping(value = "{grupoId}/estudiantes/{estudianteId}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public GrupoDetailedDTO addEstudiante(@PathVariable Long grupoId, @PathVariable Long estudianteId) throws EntityNotFoundException {
        GrupoEntity e = grupoEstudianteService.addEstudiante(grupoId, estudianteId);
        return modelMapper.map(e, GrupoDetailedDTO.class);
    }

    /** Elimina estudiante de un grupo
     * 
     * @param grupoId
     * @param estudianteId
     * @throws EntityNotFoundException
     */
    @DeleteMapping(value = "{grupoId}/estudiantes/{estudianteId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteEstudiante(@PathVariable Long grupoId, @PathVariable Long estudianteId) throws EntityNotFoundException {
        grupoEstudianteService.removeEstudiante(grupoId, estudianteId);
    }
    
    
}

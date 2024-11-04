package com.dsw.students_grades.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.dsw.students_grades.dto.CoEvaluacionDTO;
import com.dsw.students_grades.dto.EstudianteDetailedDTO;
import com.dsw.students_grades.entities.EstudianteEntity;
import com.dsw.students_grades.entities.CoEvaluacionEntity;
import com.dsw.students_grades.exceptions.EntityNotFoundException;
import com.dsw.students_grades.exceptions.IllegalOperationException;
import com.dsw.students_grades.services.EstudianteCalificadoCoevaluacionService;


@RestController
@RequestMapping("/estudianteCalificado")
public class EstudianteCalificadoCoevaluacionController {

    @Autowired
    EstudianteCalificadoCoevaluacionService estudianteCalificadoCoEvaluacionService;

    @Autowired
    private ModelMapper modelMapper;

    /** Obtiene las coEvaluaciones de un estudiante
     * 
     * @param estudianteId
     * @return Lista de coEvaluaciones mapeadaos al DTO
     * @throws EntityNotFoundException
     */
    @GetMapping(value = "{estudianteId}/coEvaluaciones")
    @ResponseStatus(code = HttpStatus.OK)
    public List<CoEvaluacionDTO> getEvaluacionesRecibidas(@PathVariable Long estudianteId) throws EntityNotFoundException {
        List<CoEvaluacionEntity> list = estudianteCalificadoCoEvaluacionService.getEvaluacionesRecibidas(estudianteId);
        return modelMapper.map(list, new org.modelmapper.TypeToken<List<CoEvaluacionDTO>>() {}.getType());
    }

    /** Obtiene una coEvaluacion de un estudiante calificador
     * 
     * @param estudianteId
     * @return CoEvaluacion mapeado al DTO
     * @throws EntityNotFoundException
     * @throws IllegalOperationException 
     */

    @GetMapping(value = "{estudianteId}/coEvaluaciones/{coEvaluacionId}")
    @ResponseStatus(code = HttpStatus.OK)
    public CoEvaluacionDTO getCoEvaluacion(@PathVariable Long estudianteId, @PathVariable Long coEvaluacionId) throws EntityNotFoundException, IllegalOperationException {
        CoEvaluacionEntity e = estudianteCalificadoCoEvaluacionService.getCoEvaluacion(estudianteId, coEvaluacionId);
        return modelMapper.map(e, CoEvaluacionDTO.class);
    }

    /** Añade coEvaluacion a estudiante
     * 
     * @param estudianteId
     * @param coEvaluacionId
     * @return Estudiante detallado mapeado a DTO
     * @throws EntityNotFoundException
     */
    @PostMapping(value = "{estudianteId}/coEvaluaciones/{coEvaluacionId}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public EstudianteDetailedDTO addCoEvaluacion(@PathVariable Long estudianteId, @PathVariable Long coEvaluacionId) throws EntityNotFoundException {
        EstudianteEntity e = estudianteCalificadoCoEvaluacionService.addCoEvaluacion(estudianteId, coEvaluacionId);
        return modelMapper.map(e, EstudianteDetailedDTO.class);
    }

    /** Elimina coEvaluacion de un estudiante
     * 
     * @param estudianteId
     * @param coEvaluacionId
     * @throws EntityNotFoundException
     */
    @DeleteMapping(value = "{estudianteId}/coEvaluaciones/{coEvaluacionId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteCoEvaluacion(@PathVariable Long estudianteId, @PathVariable Long coEvaluacionId) throws EntityNotFoundException {
        estudianteCalificadoCoEvaluacionService.removeCoEvaluacion(estudianteId, coEvaluacionId);
    }
    
    
}

package com.dsw.students_grades.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.dsw.students_grades.dto.CoEvaluacionDTO;
import com.dsw.students_grades.entities.CoEvaluacionEntity;
import com.dsw.students_grades.exceptions.EntityNotFoundException;
import com.dsw.students_grades.exceptions.IllegalOperationException;
import com.dsw.students_grades.services.CoEvaluacionService;

@RestController
@RequestMapping("/coevaluaciones")
public class CoEvaluacionController {
    @Autowired
    private CoEvaluacionService coevaluacionService;

    @Autowired
    private ModelMapper modelMapper;

    /** Obtiene un coevaluacion de la base de datos
     * 
     * @param id
     * @return CoEvaluacion mapeado al DTO
     * @throws EntityNotFoundException
     */
    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public CoEvaluacionDTO getCoEvaluacion(@PathVariable Long id) throws EntityNotFoundException {
        CoEvaluacionEntity coevaluacion  = coevaluacionService.getCoEvaluacion(id);
        return modelMapper.map(coevaluacion, CoEvaluacionDTO.class);
    }

    /** Obtiene todos los coevaluaciones de la base de datos
     * 
     * @return Lista de coevaluaciones mapeados al DTO
     * @throws EntityNotFoundException
     */
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<CoEvaluacionDTO> getCoEvaluacions() throws EntityNotFoundException {
        List<CoEvaluacionEntity> coevaluacion  = coevaluacionService.getCoEvaluaciones();
        return modelMapper.map(coevaluacion, new org.modelmapper.TypeToken<List<CoEvaluacionDTO>>() {}.getType());
    }

    /** Crea un coevaluacion en base a lo encontrado a la base de datos
     * 
     * @param coevaluacion DTO de coevaluacion a crear
     * @return CoEvaluacion creado mapeado al DTO
     * @throws IllegalOperationException
     */
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CoEvaluacionDTO createCoEvaluacion(@RequestBody CoEvaluacionDTO coevaluacion) throws IllegalOperationException {
        CoEvaluacionEntity coevaluacionEntity = modelMapper.map(coevaluacion, CoEvaluacionEntity.class);
        CoEvaluacionEntity newCoEvaluacion = coevaluacionService.createCoEvaluacion(coevaluacionEntity);
        return modelMapper.map(newCoEvaluacion,CoEvaluacionDTO.class);
    }
    /**
     * 
     * @param id Id del coevaluacion a actualizar
     * @param coevaluacion DTO con la nueva información del coevaluacion
     * @return CoEvaluacion actualizado mapeado al DTO
     * @throws EntityNotFoundException
     * @throws IllegalOperationException
     */
    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public CoEvaluacionDTO updateCoEvaluacion(@PathVariable Long id, @RequestBody CoEvaluacionDTO coevaluacion) throws EntityNotFoundException, IllegalOperationException {
        CoEvaluacionEntity coevaluacionEntity = modelMapper.map(coevaluacion, CoEvaluacionEntity.class);
        CoEvaluacionEntity updatedCoEvaluacion = coevaluacionService.updateCoEvaluacion(id , coevaluacionEntity);
        return modelMapper.map(updatedCoEvaluacion,CoEvaluacionDTO.class);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteCoEvaluacion(@PathVariable Long id) throws EntityNotFoundException, IllegalOperationException{
        coevaluacionService.deleteCoEvaluacion(id);
    }
    
    
}

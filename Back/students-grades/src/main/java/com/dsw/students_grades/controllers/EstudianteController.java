package com.dsw.students_grades.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.dsw.students_grades.dto.EstudianteDTO;
import com.dsw.students_grades.entities.EstudianteEntity;
import com.dsw.students_grades.exceptions.EntityNotFoundException;
import com.dsw.students_grades.exceptions.IllegalOperationException;
import com.dsw.students_grades.services.EstudianteService;




@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {
    @Autowired
    private EstudianteService estudianteService;

    @Autowired
    private ModelMapper modelMapper;

    /** Obtiene un estudiante de la base de datos
     * 
     * @param id
     * @return Estudiante mapeado al DTO
     * @throws EntityNotFoundException
     */
    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public EstudianteDTO getEstudiante(@PathVariable Long id) throws EntityNotFoundException {
        EstudianteEntity estudiante  = estudianteService.getEstudiante(id);
        return modelMapper.map(estudiante, EstudianteDTO.class);
    }

    /** Obtiene todos los estudiantes de la base de datos
     * 
     * @return Lista de estudiantes mapeados al DTO
     * @throws EntityNotFoundException
     */
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<EstudianteDTO> getEstudiantes() throws EntityNotFoundException {
        List<EstudianteEntity> estudiante  = estudianteService.getEstudiantes();
        return modelMapper.map(estudiante, new org.modelmapper.TypeToken<List<EstudianteDTO>>() {}.getType());
    }

    /** Crea un estudiante en base a lo encontrado a la base de datos
     * 
     * @param estudiante DTO de estudiante a crear
     * @return Estudiante creado mapeado al DTO
     * @throws IllegalOperationException
     */
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public EstudianteDTO createEstudiante(@RequestBody EstudianteDTO estudiante) throws IllegalOperationException {
        EstudianteEntity estudianteEntity = modelMapper.map(estudiante, EstudianteEntity.class);
        EstudianteEntity newEstudiante = estudianteService.createEstudiante(estudianteEntity);
        return modelMapper.map(newEstudiante,EstudianteDTO.class);
    }
    /**
     * 
     * @param id Id del estudiante a actualizar
     * @param estudiante DTO con la nueva información del estudiante
     * @return Estudiante actualizado mapeado al DTO
     * @throws EntityNotFoundException
     * @throws IllegalOperationException
     */
    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public EstudianteDTO updateEstudiante(@PathVariable Long id, @RequestBody EstudianteDTO estudiante) throws EntityNotFoundException, IllegalOperationException {
        EstudianteEntity estudianteEntity = modelMapper.map(estudiante, EstudianteEntity.class);
        EstudianteEntity updatedEstudiante = estudianteService.updateEstudiante(id , estudianteEntity);
        return modelMapper.map(updatedEstudiante,EstudianteDTO.class);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteEstudiante(@PathVariable Long id) throws EntityNotFoundException, IllegalOperationException{
        estudianteService.deleteEstudiante(id);
    }
    
    
}

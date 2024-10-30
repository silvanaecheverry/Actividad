package com.dsw.students_grades.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.dsw.students_grades.dto.GrupoDTO;
import com.dsw.students_grades.entities.GrupoEntity;
import com.dsw.students_grades.exceptions.EntityNotFoundException;
import com.dsw.students_grades.exceptions.IllegalOperationException;
import com.dsw.students_grades.services.GrupoService;

@RestController
@RequestMapping("/grupos")
public class GrupoController {
    @Autowired
    private GrupoService grupoService;

    @Autowired
    private ModelMapper modelMapper;

    /** Obtiene un grupo de la base de datos
     * 
     * @param id
     * @return Grupo mapeado al DTO
     * @throws EntityNotFoundException
     */
    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public GrupoDTO getGrupo(@PathVariable Long id) throws EntityNotFoundException {
        GrupoEntity grupo  = grupoService.getGrupo(id);
        return modelMapper.map(grupo, GrupoDTO.class);
    }

    /** Obtiene todos los grupos de la base de datos
     * 
     * @return Lista de grupos mapeados al DTO
     * @throws EntityNotFoundException
     */
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<GrupoDTO> getGrupos() throws EntityNotFoundException {
        List<GrupoEntity> grupo  = grupoService.getGrupos();
        return modelMapper.map(grupo, new org.modelmapper.TypeToken<List<GrupoDTO>>() {}.getType());
    }

    /** Crea un grupo en base a lo encontrado a la base de datos
     * 
     * @param grupo DTO de grupo a crear
     * @return Grupo creado mapeado al DTO
     * @throws IllegalOperationException
     */
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public GrupoDTO createGrupo(@RequestBody GrupoDTO grupo) throws IllegalOperationException {
        GrupoEntity grupoEntity = modelMapper.map(grupo, GrupoEntity.class);
        GrupoEntity newGrupo = grupoService.createGrupo(grupoEntity);
        return modelMapper.map(newGrupo,GrupoDTO.class);
    }
    /**
     * 
     * @param id Id del grupo a actualizar
     * @param grupo DTO con la nueva información del grupo
     * @return Grupo actualizado mapeado al DTO
     * @throws EntityNotFoundException
     * @throws IllegalOperationException
     */
    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public GrupoDTO updateGrupo(@PathVariable Long id, @RequestBody GrupoDTO grupo) throws EntityNotFoundException, IllegalOperationException {
        GrupoEntity grupoEntity = modelMapper.map(grupo, GrupoEntity.class);
        GrupoEntity updatedGrupo = grupoService.updateGrupo(id , grupoEntity);
        return modelMapper.map(updatedGrupo,GrupoDTO.class);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteGrupo(@PathVariable Long id) throws EntityNotFoundException, IllegalOperationException{
        grupoService.deleteGrupo(id);
    }
    
    
}

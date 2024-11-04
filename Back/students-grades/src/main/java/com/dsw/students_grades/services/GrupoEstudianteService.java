package com.dsw.students_grades.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dsw.students_grades.entities.EstudianteEntity;
import com.dsw.students_grades.entities.GrupoEntity;
import com.dsw.students_grades.exceptions.EntityNotFoundException;
import com.dsw.students_grades.exceptions.ErrorMessage;
import com.dsw.students_grades.exceptions.IllegalOperationException;
import com.dsw.students_grades.repositories.EstudianteRepository;
import com.dsw.students_grades.repositories.GrupoRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Clase que implementa la relación entre grupo y estudiante.
 *
 * @author Omar David Toledo Leguizamón
 */

@Slf4j
@Service
public class GrupoEstudianteService {

    @Autowired
    EstudianteRepository estudianteRepository;

    @Autowired
    GrupoRepository grupoRepository;

    /** Añade estudiante a un grupo
     * 
     * @param grupoId id del grupo al que se le agregara el estudiante
     * @param estudianteId id del estudiante que será agregado al grupo
     * @return Grupo al que se le añadio el estudiante
     * @throws EntityNotFoundException
     */
    @Transactional
    public GrupoEntity addEstudiante(long grupoId, long estudianteId) throws EntityNotFoundException{
        log.info("Inicia proceso de añadir estudiante con id = {} a grupo con id = {}",estudianteId,grupoId);
        Optional<EstudianteEntity> toAdd = estudianteRepository.findById(estudianteId);
        Optional<GrupoEntity> grupo = grupoRepository.findById(grupoId);
        if(toAdd.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.ESTUDIANTE_NOT_FOUND);
        }

        if(grupo.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.GRUPO_NOT_FOUND);
        }

        if(!grupo.get().getEstudiantes().contains(toAdd.get())){
            grupo.get().getEstudiantes().add(toAdd.get());
            toAdd.get().setGrupo(grupo.get());
        }
        log.info("Termina proceso de añadir estudiante con id = {} a grupo con id = {}",estudianteId,grupoId);
        return grupo.get();

    }

    /** Elimina estudiante a un grupo
     * 
     * @param grupoId id del grupo al que se le eliminara el estudiante
     * @param estudianteId id del estudiante que será eliminado al grupo
     * @return Grupo al que se le elimino el estudiante
     * @throws EntityNotFoundException
     */
    @Transactional
    public GrupoEntity removeEstudiante(long grupoId, long estudianteId) throws EntityNotFoundException{
        log.info("Inicia proceso de eliminar estudiante con id = {} del grupo con id = {}",estudianteId,grupoId);
        Optional<EstudianteEntity> toRemove = estudianteRepository.findById(estudianteId);
        Optional<GrupoEntity> grupo = grupoRepository.findById(grupoId);
        if(toRemove.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.ESTUDIANTE_NOT_FOUND);
        }

        if(grupo.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.GRUPO_NOT_FOUND);
        }

        if(grupo.get().getEstudiantes().contains(toRemove.get())){
            grupo.get().getEstudiantes().remove(toRemove.get());
            toRemove.get().setGrupo(null);
        }
        log.info("Termina proceso de eliminar estudiante con id = {} del grupo con id = {}",estudianteId,grupoId);
        return grupo.get();

    }

    /** Obtiene los estudiantes de un grupo
     * 
     * @param grupoId Grupo del que se busca obtener sus estuidnates
     * @return Lista de estudiantes asociados al grupo
     * @throws EntityNotFoundException
     */
    @Transactional
    public List<EstudianteEntity> getEstudiantes(long grupoId) throws EntityNotFoundException{
        log.info("Inicia proceso de obtener estudiantes de grupo con id = {}",grupoId);

        Optional<GrupoEntity> grupo = grupoRepository.findById(grupoId);

        if(grupo.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.GRUPO_NOT_FOUND);
        }
        log.info("Termina proceso de obtener estudiantes de grupo con id = {}",grupoId);
        return grupo.get().getEstudiantes();

    }

    /** Obtiene estudiante asociado a un grupo
     * 
     * @param grupoId id del grupo en el que se buscará el estudiante
     * @param estudianteId id de estudiante a obtener
     * @return estudiante asociado el grupo
     * @throws EntityNotFoundException
     * @throws IllegalOperationException
     */
    @Transactional
    public EstudianteEntity getEstudiante(long grupoId, long estudianteId) throws EntityNotFoundException, IllegalOperationException{
        log.info("Inicia proceso de obtener estudiante con id = {} de grupo con id = {}",estudianteId,grupoId);
        Optional<EstudianteEntity> toGet = estudianteRepository.findById(estudianteId);
        Optional<GrupoEntity> grupo = grupoRepository.findById(grupoId);
        if(toGet.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.ESTUDIANTE_NOT_FOUND);
        }

        if(grupo.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.GRUPO_NOT_FOUND);
        }

        if(!grupo.get().getEstudiantes().contains(toGet.get())){
            throw new IllegalOperationException("El estudiante no está asociado al grupo");
        }
        log.info("Termina proceso de obtener estudiante con id = {} de grupo con id = {}",estudianteId,grupoId);
        return toGet.get();
    }

    
    
}

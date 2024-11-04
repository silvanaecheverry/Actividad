package com.dsw.students_grades.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dsw.students_grades.entities.CoEvaluacionEntity;
import com.dsw.students_grades.entities.EstudianteEntity;
import com.dsw.students_grades.exceptions.EntityNotFoundException;
import com.dsw.students_grades.exceptions.ErrorMessage;
import com.dsw.students_grades.exceptions.IllegalOperationException;
import com.dsw.students_grades.repositories.CoEvaluacionRepository;
import com.dsw.students_grades.repositories.EstudianteRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Clase que implementa la relación entre estudiante calificador y coEvaluacion.
 *
 * @author Omar David Toledo Leguizamón
 */

@Slf4j
@Service
public class EstudianteCalificadorCoevaluacionService {

    @Autowired
    CoEvaluacionRepository coEvaluacionRepository;

    @Autowired
    EstudianteRepository estudianteRepository;

    /** Añade coEvaluacion a un estudiante
     * 
     * @param estudianteId id del estudiante al que se le agregara la coEvaluacion
     * @param coEvaluacionId id de la coEvaluacion que será agregado al estudiante
     * @return Estudiante al que se le añadio la coEvaluacion
     * @throws EntityNotFoundException
     */
    @Transactional
    public EstudianteEntity addCoEvaluacion(long estudianteId, long coEvaluacionId) throws EntityNotFoundException{
        log.info("Inicia proceso de añadir coEvaluacion con id = {} a estudiante calificador con id = {}",coEvaluacionId,estudianteId);
        Optional<CoEvaluacionEntity> toAdd = coEvaluacionRepository.findById(coEvaluacionId);
        Optional<EstudianteEntity> estudiante = estudianteRepository.findById(estudianteId);
        if(toAdd.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.COEVALUACION_NOT_FOUND);
        }

        if(estudiante.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.GRUPO_NOT_FOUND);
        }

        if(!estudiante.get().getEvaluacionesHechas().contains(toAdd.get())){
            estudiante.get().getEvaluacionesHechas().add(toAdd.get());
            toAdd.get().setEstudianteCalificador(estudiante.get());
        }
        log.info("Termina proceso de añadir coEvaluacion con id = {} a estudiante calificador con id = {}",coEvaluacionId,estudianteId);
        return estudiante.get();

    }

    /** Elimina coEvaluacion a un estudiante
     * 
     * @param estudianteId id del estudiante al que se le eliminara el coEvaluacion
     * @param coEvaluacionId id del coEvaluacion que será eliminado al estudiante
     * @return Estudiante al que se le elimino el coEvaluacion
     * @throws EntityNotFoundException
     */
    @Transactional
    public EstudianteEntity removeCoEvaluacion(long estudianteId, long coEvaluacionId) throws EntityNotFoundException{
        log.info("Inicia proceso de eliminar coEvaluacion con id = {} del estudiante con id = {}",coEvaluacionId,estudianteId);
        Optional<CoEvaluacionEntity> toRemove = coEvaluacionRepository.findById(coEvaluacionId);
        Optional<EstudianteEntity> estudiante = estudianteRepository.findById(estudianteId);
        if(toRemove.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.COEVALUACION_NOT_FOUND);
        }

        if(estudiante.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.GRUPO_NOT_FOUND);
        }

        if(estudiante.get().getEvaluacionesHechas().contains(toRemove.get())){
            estudiante.get().getEvaluacionesHechas().remove(toRemove.get());
            toRemove.get().setEstudianteCalificador(null);
        }
        log.info("Termina proceso de eliminar coEvaluacion con id = {} del estudiante con id = {}",coEvaluacionId,estudianteId);
        return estudiante.get();

    }

    /** Obtiene las coEvaluaciones de un estudiante
     * 
     * @param estudianteId Estudiante del que se busca obtener sus coevaluaciones hechas
     * @return Lista de coEvaluaciones asociados al estudiante calificador
     * @throws EntityNotFoundException
     */
    @Transactional
    public List<CoEvaluacionEntity> getEvaluacionesHechas(long estudianteId) throws EntityNotFoundException{
        log.info("Inicia proceso de obtener coEvaluaciones de estudiante con id = {}",estudianteId);

        Optional<EstudianteEntity> estudiante = estudianteRepository.findById(estudianteId);

        if(estudiante.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.GRUPO_NOT_FOUND);
        }
        log.info("Termina proceso de obtener coEvaluaciones de estudiante con id = {}",estudianteId);
        return estudiante.get().getEvaluacionesHechas();

    }

    /** Obtiene coEvaluacion realizada asociado a un estudiante
     * 
     * @param estudianteId id del estudiante en el que se buscará el coEvaluacion
     * @param coEvaluacionId id de coEvaluacion a obtener
     * @return coEvaluacion asociada a el estudiante
     * @throws EntityNotFoundException
     * @throws IllegalOperationException
     */
    @Transactional
    public CoEvaluacionEntity getCoEvaluacion(long estudianteId, long coEvaluacionId) throws EntityNotFoundException, IllegalOperationException{
        log.info("Inicia proceso de obtener coEvaluacion con id = {} de estudiante con id = {}",coEvaluacionId,estudianteId);
        Optional<CoEvaluacionEntity> toGet = coEvaluacionRepository.findById(coEvaluacionId);
        Optional<EstudianteEntity> estudiante = estudianteRepository.findById(estudianteId);
        if(toGet.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.COEVALUACION_NOT_FOUND);
        }

        if(estudiante.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.GRUPO_NOT_FOUND);
        }

        if(!estudiante.get().getEvaluacionesHechas().contains(toGet.get())){
            throw new IllegalOperationException("La coEvaluacion no está asociado al estudiante");
        }
        log.info("Termina proceso de obtener coEvaluacion con id = {} de estudiante con id = {}",coEvaluacionId,estudianteId);
        return toGet.get();
    }

    
    
}

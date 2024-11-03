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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CoEvaluacionService {

    @Autowired
    CoEvaluacionRepository coEvaluacionRepository;

    /**
     * Guarda una nueva coevaluacion.
     *
     * @param estudiante La entidad de tipo coevaluacion de la nueva coevaluacion a persistir.
     * @return La entidad luego de persistirla.
     * @throws IllegalOperationException Si el comentario es nulo o vacio.
     * @throws IllegalOperationException Si calificacion es nula o no esta entre 0 y 5.
     * @throws IllegalOperationException Si la fecha es invalida.
     */
    @Transactional
    public CoEvaluacionEntity createCoEvaluacion(CoEvaluacionEntity coEvaluacion)
        throws IllegalOperationException{
            log.info("Inicia proceso de creación de la coevaluacion");

            if((coEvaluacion.getComentario() == null) || (coEvaluacion.getComentario().isEmpty()))
                    throw new IllegalOperationException("El comentario no puede ser nulo o vacio");

            if(coEvaluacion.getFecha() == null)
                    throw new IllegalOperationException("La fecha no puede ser nula");

            if((coEvaluacion.getCalificacion() == null) || (coEvaluacion.getCalificacion() > 5.0) || (coEvaluacion.getCalificacion() < 0))
                    throw new IllegalOperationException("La calificacion no puede ser nula y debe estar entre 0 y 5.0");

            log.info("Termina proceso de creación de la coevaluacion");
            return coEvaluacionRepository.save(coEvaluacion);
    }

    /**
	 * Devuelve todas las coevaluaciones que hay en la base de datos.
	 *
	 * @return Lista de entidades de tipo coevaluacion.
	 */
    @Transactional
    public List<CoEvaluacionEntity> getCoEvaluaciones() {
        log.info("Inicia proceso de consultar todas las coevaluaciones");
        return coEvaluacionRepository.findAll();
    }

    /**
	 * Busca una coevaluacion por ID.
	 *
	 * @param  CoEvaluacionId El id de la coevaluacion a buscar.
	 * @return La coevaluacion encontrado.
	 * @throws EntityNotFoundException Si la coevaluacion no existe.
	 */
    @Transactional
    public CoEvaluacionEntity getCoEvaluacion(Long coevaluacionId)
        throws EntityNotFoundException {
            log.info("Inicia proceso de consultar coevaluacion con id = {}", coevaluacionId);
            Optional<CoEvaluacionEntity> coevaluacionEntity = coEvaluacionRepository.findById(coevaluacionId);
            if (coevaluacionEntity.isEmpty())
                throw new EntityNotFoundException(ErrorMessage.COEVALUACION_NOT_FOUND);
            log.info("Termina proceso de consultar coevaluacion con id = {}", coevaluacionId);
            return coevaluacionEntity.get();
    }

    /**
     * Actualiza una CoEvaluacion por ID.
     *
     * @paaram  CoEvaluacionId El ID de la CoEvaluacion a actualizar
     * @param  CoEvaluacion La entidad del  CoEvaluacion con los cambios deseados.
     * @return La entidad del  CoEvaluacion luego de actualizarla.
     * @throws EntityNotFoundException Si la  CoEvaluacion no existe.
     * @throws IllegalOperationException Si el comentario es nulo o vacio.
     * @throws IllegalOperationException Si calificacion es nula o no esta entre 0 y 5.
     * @throws IllegalOperationException Si la fecha es invalida.
     */
    @Transactional
    public  CoEvaluacionEntity updateCoEvaluacion(Long  coEvaluacionId,  CoEvaluacionEntity  coEvaluacion)
        throws EntityNotFoundException, IllegalOperationException{
            log.info("Inicia proceso de actualizar la coevaluacion con id = {}",  coEvaluacionId);
            Optional< CoEvaluacionEntity>  coEvaluacionEntity =  coEvaluacionRepository.findById( coEvaluacionId);
            if (coEvaluacionEntity.isEmpty())
                    throw new EntityNotFoundException(ErrorMessage.COEVALUACION_NOT_FOUND);

            if((coEvaluacion.getComentario() == null) || (coEvaluacion.getComentario().isEmpty()))
                    throw new IllegalOperationException("El comentario no puede ser nulo o vacio");

            if(coEvaluacion.getFecha() == null)
                    throw new IllegalOperationException("La fecha no puede ser nula");

            if((coEvaluacion.getCalificacion() == null) || (coEvaluacion.getCalificacion() > 5.0) || (coEvaluacion.getCalificacion() < 0))
                    throw new IllegalOperationException("La calificacion no puede ser nula y debe estar entre 0 y 5.0");


            coEvaluacion.setId( coEvaluacionId);

            log.info("Termina proceso de actualizar la coevaluacion con id = {}",  coEvaluacionId);
            return  coEvaluacionRepository.save( coEvaluacion);
    }

    /**
     * Eliminar una coevaluacion por Id.
     * 
     * @param coevaluacionId El ID del CoEvaluacion a eliminar.
     * @throws EntityNotFoundException Si la entidad no se encuentra
     * @throws IllegalOperationException Si alguna relacion existe
     */
    @Transactional
    public void deleteCoEvaluacion(Long grupoId)
        throws EntityNotFoundException, IllegalOperationException{
        log.info("Inicia proceso de borrar el coevaluacion con id = {}", grupoId);
        Optional<CoEvaluacionEntity> grupoEntity = coEvaluacionRepository.findById(grupoId);
        if (grupoEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.COEVALUACION_NOT_FOUND);
        
        EstudianteEntity estudianteCalificado = grupoEntity.get().getEstudianteCalificado();
        EstudianteEntity estudianteCalificador = grupoEntity.get().getEstudianteCalificador();
        if (estudianteCalificado !=  null || estudianteCalificador !=  null)
            throw new IllegalOperationException("No se puede eliminar la coevaluacion"+
                " porque tiene estudiantes asociados");

        coEvaluacionRepository.deleteById(grupoId);
        log.info("Termina proceso de borrar el coevaluacion con id = {}", grupoId);
    }
}

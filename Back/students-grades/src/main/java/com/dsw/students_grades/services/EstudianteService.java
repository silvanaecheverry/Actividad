package com.dsw.students_grades.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dsw.students_grades.entities.CoEvaluacionEntity;
import com.dsw.students_grades.entities.EstudianteEntity;
import com.dsw.students_grades.entities.GrupoEntity;
import com.dsw.students_grades.exceptions.EntityNotFoundException;
import com.dsw.students_grades.exceptions.ErrorMessage;
import com.dsw.students_grades.exceptions.IllegalOperationException;
import com.dsw.students_grades.repositories.EstudianteRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * Estudiante.
 *
 * @author Jeronimo A. Pineda Cano
 */

@Slf4j
@Service
public class EstudianteService {

    @Autowired
    EstudianteRepository estudianteRepository;

    public boolean isLoginUsed(String login){
        List<EstudianteEntity> estudianteList = estudianteRepository.findByLogin(login);
        return !estudianteList.isEmpty();
    }

    /**
     * Guarda un nuevo estudiante.
     *
     * @param estudiante La entidad de tipo estudiante del nuevo estudiante a persistir.
     * @return La entidad luego de persistirla.
     * @throws IllegalOperationException Si el login es nulo o vacio.
     * @throws IllegalOperationException Si el nombre es nulo o vacio.
     */
    @Transactional
    public EstudianteEntity createEstudiante(EstudianteEntity estudiante)
        throws IllegalOperationException{
            log.info("Inicia proceso de creación del estudiante");

            if((estudiante.getLogin() == null) || (estudiante.getLogin().isEmpty()))
                    throw new IllegalOperationException("El login no puede ser nulo o vacio");

            if(isLoginUsed(estudiante.getLogin()))
                    throw new IllegalOperationException("El login ya esta usado");

            if((estudiante.getNombre() == null) || (estudiante.getNombre().isEmpty()))
                    throw new IllegalOperationException("El nombre no puede ser nulo o vacio");

            log.info("Termina proceso de creación del estudiante");
            return estudianteRepository.save(estudiante);
    }

    /**
	 * Devuelve todos los estudiantes que hay en la base de datos.
	 *
	 * @return Lista de entidades de tipo estudiante.
	 */
    @Transactional
    public List<EstudianteEntity> getEstudiantes() {
        log.info("Inicia proceso de consultar todos los estudiantes");
        return estudianteRepository.findAll();
    }

    /**
	 * Busca un estudiante por ID.
	 *
	 * @param estudianteId El id del estudiante a buscar.
	 * @return El estudiante encontrado.
	 * @throws EntityNotFoundException Si el estudiante no existe.
	 */
    @Transactional
    public EstudianteEntity getEstudiante(Long estudianteId)
        throws EntityNotFoundException {
            log.info("Inicia proceso de consultar estudiante con id = {}", estudianteId);
            Optional<EstudianteEntity> estudianteEntity = estudianteRepository.findById(estudianteId);
            if (estudianteEntity.isEmpty())
                throw new EntityNotFoundException(ErrorMessage.ESTUDIANTE_NOT_FOUND);
            log.info("Termina proceso de consultar estudiante con id = {}", estudianteId);
            return estudianteEntity.get();
    }

    @Transactional
    public EstudianteEntity getEstudiantebyLogin(String login)
        throws EntityNotFoundException {
            log.info("Inicia proceso de consultar estudiante con login = {}", login);
            List<EstudianteEntity> estudianteList = estudianteRepository.findByLogin(login);
            if (estudianteList.isEmpty())
                throw new EntityNotFoundException(ErrorMessage.ESTUDIANTE_NOT_FOUND);
            log.info("Termina proceso de consultar estudiante con login = {}", login);
            return estudianteList.get(0);
    }

    /**
     * Actualiza un estudiante por ID.
     *
     * @paaram estudianteId El ID del estudiante a actualizar
     * @param estudiante La entidad del estudiante con los cambios deseados.
     * @return La entidad del estudiante luego de actualizarla.
     * @throws EntityNotFoundException Si el estudiante no existe.
     * @throws IllegalOperationException Si el login es nulo o vacio.
     * @throws IllegalOperationException Si el nombre es nulo o vacio.
     */
    @Transactional
    public EstudianteEntity updateEstudiante(Long estudianteId, EstudianteEntity estudiante)
        throws EntityNotFoundException, IllegalOperationException{
            log.info("Inicia proceso de actualizar el estudiante con id = {}", estudianteId);
            Optional<EstudianteEntity> estudianteEntity = estudianteRepository.findById(estudianteId);
            if (estudianteEntity.isEmpty())
                    throw new EntityNotFoundException(ErrorMessage.ESTUDIANTE_NOT_FOUND);

            if((estudiante.getLogin() == null) || (estudiante.getLogin().isEmpty()))
                    throw new IllegalOperationException("El login no puede ser nulo o vacio");

            if((estudiante.getNombre() == null) || (estudiante.getNombre().isEmpty()))
                    throw new IllegalOperationException("El nombre no puede ser nulo o vacio");

            estudiante.setId(estudianteId);

            log.info("Termina proceso de actualizar el estudiante con id = {}", estudianteId);
            return estudianteRepository.save(estudiante);
    }

    /**
     * Eliminar un estudiante por Id.
     * 
     * @param estudianteId El ID del estudiante a eliminar.
     * @throws EntityNotFoundException Si la entidad no se encuentra
     * @throws IllegalOperationException Si alguna relacion existe
     */
    @Transactional
    public void deleteEstudiante(Long estudianteId)
        throws EntityNotFoundException, IllegalOperationException{
        log.info("Inicia proceso de borrar el estudiante con id = {}", estudianteId);
        Optional<EstudianteEntity> estudianteEntity = estudianteRepository.findById(estudianteId);
        if (estudianteEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.ESTUDIANTE_NOT_FOUND);
        
        List<CoEvaluacionEntity> evaluacionesHechas = estudianteEntity.get().getEvaluacionesHechas();
        if (!evaluacionesHechas.isEmpty())
            throw new IllegalOperationException("No se puede eliminar al estudiante"+
                " porque tiene citas asociadas");

        List<CoEvaluacionEntity> evaluacionesRecibidas = estudianteEntity.get().getEvaluacionesRecibidas();
        if (!evaluacionesRecibidas.isEmpty())
            throw new IllegalOperationException("No se puede eliminar al estudiante"+
                " porque tiene indicadores asociados");

        GrupoEntity grupo = estudianteEntity.get().getGrupo();
        if (grupo != null)
            throw new IllegalOperationException("No se puede eliminar al estudiante"+
                " porque tiene un grupo asociado");

        estudianteRepository.deleteById(estudianteId);
        log.info("Termina proceso de borrar el estudiante con id = {}", estudianteId);
    }
    
}
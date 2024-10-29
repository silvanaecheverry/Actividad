package com.dsw.students_grades.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dsw.students_grades.entities.GrupoEntity;
import com.dsw.students_grades.entities.EstudianteEntity;
import com.dsw.students_grades.exceptions.EntityNotFoundException;
import com.dsw.students_grades.exceptions.ErrorMessage;
import com.dsw.students_grades.exceptions.IllegalOperationException;
import com.dsw.students_grades.repositories.GrupoRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * Grupo.
 *
 * @author Omar David Toledo Leguizamón
 */

@Slf4j
@Service
public class GrupoService {

    @Autowired
    GrupoRepository grupoRepository;

    /**
     * Guarda un nuevo Grupo.
     *
     * @param Grupo La entidad de tipo Grupo del nuevo Grupo a persistir.
     * @return La entidad luego de persistirla.
     * @throws IllegalOperationException Si el Numero es nulo.
     * @throws IllegalOperationException Si el nombre es nulo o vacio.
     */
    @Transactional
    public GrupoEntity createGrupo(GrupoEntity grupo)
        throws IllegalOperationException{
            log.info("Inicia proceso de creación del Grupo");

            if((grupo.getNumero() == null))
                    throw new IllegalOperationException("El Numero no puede ser nulo o vacio");

            if((grupo.getNombre() == null) || (grupo.getNombre().isEmpty()))
                    throw new IllegalOperationException("El nombre no puede ser nulo o vacio");

            log.info("Termina proceso de creación del Grupo");
            return grupoRepository.save(grupo);
    }

    /**
	 * Devuelve todos los Grupos que hay en la base de datos.
	 *
	 * @return Lista de entidades de tipo Grupo.
	 */
    @Transactional
    public List<GrupoEntity> getGrupos() {
        log.info("Inicia proceso de consultar todos los Grupos");
        return grupoRepository.findAll();
    }

    /**
	 * Busca un Grupo por ID.
	 *
	 * @param GrupoId El id del Grupo a buscar.
	 * @return El Grupo encontrado.
	 * @throws EntityNotFoundException Si el Grupo no existe.
	 */
    @Transactional
    public GrupoEntity getGrupo(Long grupoId)
        throws EntityNotFoundException {
            log.info("Inicia proceso de consultar Grupo con id = {}", grupoId);
            Optional<GrupoEntity> GrupoEntity = grupoRepository.findById(grupoId);
            if (GrupoEntity.isEmpty())
                throw new EntityNotFoundException(ErrorMessage.GRUPO_NOT_FOUND);
            log.info("Termina proceso de consultar Grupo con id = {}", grupoId);
            return GrupoEntity.get();
    }

    /**
     * Actualiza un Grupo por ID.
     *
     * @paaram GrupoId El ID del Grupo a actualizar
     * @param Grupo La entidad del Grupo con los cambios deseados.
     * @return La entidad del Grupo luego de actualizarla.
     * @throws EntityNotFoundException Si el Grupo no existe.
     * @throws IllegalOperationException Si el Numero es nulo o vacio.
     * @throws IllegalOperationException Si el nombre es nulo o vacio.
     */
    @Transactional
    public GrupoEntity updateGrupo(Long grupoId, GrupoEntity grupo)
        throws EntityNotFoundException, IllegalOperationException{
            log.info("Inicia proceso de actualizar el Grupo con id = {}", grupoId);
            Optional<GrupoEntity> grupoEntity = grupoRepository.findById(grupoId);
            if (grupoEntity.isEmpty())
                    throw new EntityNotFoundException(ErrorMessage.GRUPO_NOT_FOUND);

            if(grupo.getNumero() == null)
                    throw new IllegalOperationException("El Numero no puede ser nulo o vacio");

            if((grupo.getNombre() == null) || (grupo.getNombre().isEmpty()))
                    throw new IllegalOperationException("El nombre no puede ser nulo o vacio");

            grupo.setId(grupoId);

            log.info("Termina proceso de actualizar el Grupo con id = {}", grupoId);
            return grupoRepository.save(grupo);
    }

    /**
     * Eliminar un Grupo por Id.
     * 
     * @param GrupoId El ID del Grupo a eliminar.
     * @throws 
     */
    @Transactional
    public void deleteGrupo(Long grupoId)
        throws EntityNotFoundException, IllegalOperationException{
        log.info("Inicia proceso de borrar el Grupo con id = {}", grupoId);
        Optional<GrupoEntity> grupoEntity = grupoRepository.findById(grupoId);
        if (grupoEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.GRUPO_NOT_FOUND);
        
        List<EstudianteEntity> estudiantes = grupoEntity.get().getEstudiantes();
        if (!estudiantes.isEmpty())
            throw new IllegalOperationException("No se puede eliminar al Grupo"+
                " porque tiene estudiantes asociados");

        grupoRepository.deleteById(grupoId);
        log.info("Termina proceso de borrar el Grupo con id = {}", grupoId);
    }
    
}
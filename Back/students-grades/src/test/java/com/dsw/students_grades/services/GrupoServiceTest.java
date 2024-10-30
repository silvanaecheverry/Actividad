package com.dsw.students_grades.services;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import com.dsw.students_grades.entities.EstudianteEntity;
import com.dsw.students_grades.entities.GrupoEntity;
import com.dsw.students_grades.exceptions.EntityNotFoundException;
import com.dsw.students_grades.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import jakarta.transaction.Transactional;

@DataJpaTest
@Transactional
@Import(GrupoService.class)
public class GrupoServiceTest {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<EstudianteEntity> estudiantes = new ArrayList<>();
    private List<GrupoEntity> grupos = new ArrayList<>();

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from EstudianteEntity");
        entityManager.getEntityManager().createQuery("delete from GrupoEntity");
    }

    private void insertData(){
        for (int i = 0 ; i < 3; i++){
            EstudianteEntity estudiante = factory.manufacturePojo(EstudianteEntity.class);
            GrupoEntity grupo = factory.manufacturePojo(GrupoEntity.class);
            entityManager.persist(grupo);
            entityManager.persist(estudiante);
            grupos.add(grupo);
            estudiantes.add(estudiante);
        }

    }

    /**
     * Pruebas para crear grupos
     * @throws IllegalOperationException
     */
    @Test
    void createGrupo() throws IllegalOperationException {
        GrupoEntity grupo = factory.manufacturePojo(GrupoEntity.class);
        GrupoEntity newGrupo = grupoService.createGrupo(grupo);
        assertNotNull(newGrupo);

        GrupoEntity grupoBD = entityManager.find(GrupoEntity.class,newGrupo.getId());
        assertEquals(newGrupo.getId(), grupoBD.getId());
        assertEquals(newGrupo.getNombre(), grupoBD.getNombre());
        assertEquals(newGrupo.getNumero(), grupoBD.getNumero());
    }

    @Test
    void createGrupoNombreInvalido() {
        assertThrows(IllegalOperationException.class, () -> {
            GrupoEntity grupo = factory.manufacturePojo(GrupoEntity.class);
            grupo.setNombre(null);
            grupoService.createGrupo(grupo);
        });
    }

    @Test
    void createGrupoNumeroInvalido() {
        assertThrows(IllegalOperationException.class, () -> {
            GrupoEntity grupo = factory.manufacturePojo(GrupoEntity.class);
            grupo.setNumero(null);
            grupoService.createGrupo(grupo);
        });
    }

    @Test
    void createGrupoNombreInvalido1() {
        assertThrows(IllegalOperationException.class, () -> {
            GrupoEntity grupo = factory.manufacturePojo(GrupoEntity.class);
            grupo.setNombre("");
            grupoService.createGrupo(grupo);
        });
    }

    /**
     * Pruebas para obtener todos los grupos
     */
    @Test
    void getGrupos(){
        List<GrupoEntity> list = grupoService.getGrupos();
		assertEquals(grupos.size(), list.size());
		for (GrupoEntity entity : list) {
			boolean found = false;
			for (GrupoEntity storedEntity : grupos) {
				if (entity.getId().equals(storedEntity.getId())) {
					found = true;
				}
			}
			assertTrue(found);
		}
    }

    /**
     * Pruebas para obtener un grupo con un id especifico
     * @throws EntityNotFoundException
     */
    @Test
    void getGrupo() throws EntityNotFoundException {
        GrupoEntity grupo = grupos.get(0);
        GrupoEntity grupoBD = grupoService.getGrupo(grupo.getId());

        assertEquals(grupo.getId(), grupoBD.getId());
        assertEquals(grupo.getNombre(), grupoBD.getNombre());
        assertEquals(grupo.getNumero(), grupoBD.getNumero());

    }
    
    @Test
    void getGrupoInvalido() {
        assertThrows(EntityNotFoundException.class, () -> {
            GrupoEntity grupo = grupos.get(0);
            grupo.setId(0L);
            grupoService.getGrupo(grupo.getId());
        });
    }

    /**
     * Pruebas para actualizar grupos
     * @throws IllegalOperationException
     * @throws EntityNotFoundException
     */

     @Test
     void updateGrupo() throws IllegalOperationException , EntityNotFoundException {
         GrupoEntity grupo = factory.manufacturePojo(GrupoEntity.class);
         GrupoEntity grupoOld = grupos.get(0);
         GrupoEntity updatedGrupo = grupoService.updateGrupo(grupoOld.getId(), grupo);
         assertNotNull(updatedGrupo);
 
         GrupoEntity grupoBD = entityManager.find(GrupoEntity.class,updatedGrupo.getId());
         assertEquals(updatedGrupo.getId(), grupoBD.getId());
         assertEquals(updatedGrupo.getNombre(), grupoBD.getNombre());
         assertEquals(updatedGrupo.getNumero(), grupoBD.getNumero());
     }
    
    @Test
    void updateGrupoInvalido() {
        assertThrows(EntityNotFoundException.class, () -> {
            GrupoEntity grupo = factory.manufacturePojo(GrupoEntity.class);
            GrupoEntity grupoOld = grupos.get(0);
            grupoOld.setId(0L);
            grupoService.updateGrupo(grupoOld.getId(), grupo);
        } );
    }

    @Test
    void updateGrupoNombreInvalido() {
        assertThrows(IllegalOperationException.class, () -> {
            GrupoEntity grupo = factory.manufacturePojo(GrupoEntity.class);
            GrupoEntity grupoOld = grupos.get(0);
            grupo.setNombre(null);
            grupoService.updateGrupo(grupoOld.getId(), grupo);
        } );
    }

    @Test
    void updateGrupoNombreInvalido1() {
        assertThrows(IllegalOperationException.class, () -> {
            GrupoEntity grupo = factory.manufacturePojo(GrupoEntity.class);
            GrupoEntity grupoOld = grupos.get(0);
            grupo.setNombre("");
            grupoService.updateGrupo(grupoOld.getId(), grupo);
        });
    }

    @Test
    void updateGrupoNumeroInvalido() {
        assertThrows(IllegalOperationException.class, () -> {
            GrupoEntity grupo = factory.manufacturePojo(GrupoEntity.class);
            GrupoEntity grupoOld = grupos.get(0);
            grupo.setNumero(null);
            grupoService.updateGrupo(grupoOld.getId(), grupo);
        });
    }

    /**
     * Pruebas para eliminar grupos
     * @throws IllegalOperationException
     * @throws EntityNotFoundException
     */
    
    @Test 
    void deleteGrupo() throws IllegalOperationException , EntityNotFoundException {
        GrupoEntity grupo = grupos.get(0);
        grupoService.deleteGrupo(grupo.getId());
		assertTrue(!grupoService.getGrupos().contains(grupo));
    }

    @Test
    void deleteGrupoInvalido() {
        assertThrows(EntityNotFoundException.class, () -> {
            GrupoEntity grupo = grupos.get(0);
            grupo.setId(0L);
            grupoService.deleteGrupo(grupo.getId());
        });
    }

    @Test
    void deleteGrupoEstudiantesInvalido() {
        assertThrows(IllegalOperationException.class, () -> {
            GrupoEntity grupo = grupos.get(0);
            grupo.setEstudiantes(estudiantes);
            grupoService.deleteGrupo(grupo.getId());
        });
    }
}

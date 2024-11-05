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

import com.dsw.students_grades.entities.CoEvaluacionEntity;
import com.dsw.students_grades.entities.EstudianteEntity;
import com.dsw.students_grades.entities.GrupoEntity;
import com.dsw.students_grades.exceptions.EntityNotFoundException;
import com.dsw.students_grades.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import jakarta.transaction.Transactional;

@DataJpaTest
@Transactional
@Import(EstudianteService.class)
public class EstudianteServiceTest {

    @Autowired
    private EstudianteService estudianteService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<CoEvaluacionEntity> evaluacionesHechas = new ArrayList<>();
    private List<CoEvaluacionEntity> evaluacionesRecibidas = new ArrayList<>();
    private List<EstudianteEntity> estudiantes = new ArrayList<>();

    private GrupoEntity grupo;

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from CoEvaluacionEntity");
        entityManager.getEntityManager().createQuery("delete from EstudianteEntity");
        entityManager.getEntityManager().createQuery("delete from GrupoEntity");
    }

    private void insertData(){
        for (int i = 0 ; i < 3; i++){
            CoEvaluacionEntity evaluacionHecha = factory.manufacturePojo(CoEvaluacionEntity.class);
            CoEvaluacionEntity evaluacionRecibida = factory.manufacturePojo(CoEvaluacionEntity.class);
            EstudianteEntity estudiante = factory.manufacturePojo(EstudianteEntity.class);

            entityManager.persist(evaluacionHecha);
            entityManager.persist(evaluacionRecibida);
            entityManager.persist(estudiante);

            evaluacionesHechas.add(evaluacionHecha);
            evaluacionesRecibidas.add(evaluacionRecibida);
            estudiantes.add(estudiante);
        }

        grupo = factory.manufacturePojo(GrupoEntity.class);
        entityManager.persist(grupo);

    }

    /**
     * Pruebas para crear estudiantes
     * @throws IllegalOperationException
     */
    @Test
    void createEstudiante() throws IllegalOperationException {
        EstudianteEntity estudiante = factory.manufacturePojo(EstudianteEntity.class);
        EstudianteEntity newEstudiante = estudianteService.createEstudiante(estudiante);
        assertNotNull(newEstudiante);

        EstudianteEntity estudianteBD = entityManager.find(EstudianteEntity.class,newEstudiante.getId());
        assertEquals(newEstudiante.getId(), estudianteBD.getId());
        assertEquals(newEstudiante.getNombre(), estudianteBD.getNombre());
        assertEquals(newEstudiante.getLogin(), estudianteBD.getLogin());
    }

    @Test
    void createEstudianteNombreInvalido() {
        assertThrows(IllegalOperationException.class, () -> {
            EstudianteEntity estudiante = factory.manufacturePojo(EstudianteEntity.class);
            estudiante.setNombre(null);
            estudianteService.createEstudiante(estudiante);
        }  );
    }

    @Test
    void createEstudianteLoginInvalido() {
        assertThrows(IllegalOperationException.class, () -> {
            EstudianteEntity estudiante = factory.manufacturePojo(EstudianteEntity.class);
            estudiante.setLogin(null);
            estudianteService.createEstudiante(estudiante);
        }  );
    }

    @Test
    void createEstudianteNombreInvalido1() {
        assertThrows(IllegalOperationException.class, () -> {
            EstudianteEntity estudiante = factory.manufacturePojo(EstudianteEntity.class);
            estudiante.setNombre("");
            estudianteService.createEstudiante(estudiante);
        } );
    }

    @Test
    void createEstudianteLoginInvalido1() {
        assertThrows(IllegalOperationException.class, () -> {
            EstudianteEntity estudiante = factory.manufacturePojo(EstudianteEntity.class);
            estudiante.setLogin("");
            estudianteService.createEstudiante(estudiante);
        } );
    }

    /**
     * Pruebas para obtener todos los estudiantes
     */
    @Test
    void getEstudiantes(){
        List<EstudianteEntity> list = estudianteService.getEstudiantes();
		assertEquals(estudiantes.size(), list.size());
		for (EstudianteEntity entity : list) {
			boolean found = false;
			for (EstudianteEntity storedEntity : estudiantes) {
				if (entity.getId().equals(storedEntity.getId())) {
					found = true;
				}
			}
			assertTrue(found);
		}
    }

    /**
     * Pruebas para obtener un estudiante con un login especifico
     * @throws EntityNotFoundException
     */

     @Test
     void getEstudianteLogin() throws EntityNotFoundException {
         EstudianteEntity estudiante = estudiantes.get(0);
         EstudianteEntity estudianteBD = estudianteService.getEstudiantebyLogin(estudiante.getLogin());
 
         assertEquals(estudiante.getId(), estudianteBD.getId());
         assertEquals(estudiante.getNombre(), estudianteBD.getNombre());
         assertEquals(estudiante.getLogin(), estudianteBD.getLogin());
 
     }

     @Test
     void getEstudianteLoginInvalido() throws EntityNotFoundException {
        assertThrows(EntityNotFoundException.class, () -> {
            EstudianteEntity estudiante = factory.manufacturePojo(EstudianteEntity.class);
            estudianteService.getEstudiantebyLogin(estudiante.getLogin());
        });
     }

    /**
     * Pruebas para obtener un estudiante con un id especifico
     * @throws EntityNotFoundException
     */
    @Test
    void getEstudiante() throws EntityNotFoundException {
        EstudianteEntity estudiante = estudiantes.get(0);
        EstudianteEntity estudianteBD = estudianteService.getEstudiante(estudiante.getId());

        assertEquals(estudiante.getId(), estudianteBD.getId());
        assertEquals(estudiante.getNombre(), estudianteBD.getNombre());
        assertEquals(estudiante.getLogin(), estudianteBD.getLogin());

    }
    
    @Test
    void getEstudianteInvalido() {
        assertThrows(EntityNotFoundException.class, () -> {
            EstudianteEntity estudiante = estudiantes.get(0);
            estudiante.setId(0L);
            estudianteService.getEstudiante(estudiante.getId());
        });
    }

    /**
     * Pruebas para actualizar estudiantes
     * @throws IllegalOperationException
     * @throws EntityNotFoundException
     */

     @Test
     void updateEstudiante() throws IllegalOperationException , EntityNotFoundException {
         EstudianteEntity estudiante = factory.manufacturePojo(EstudianteEntity.class);
         EstudianteEntity estudianteOld = estudiantes.get(0);
         EstudianteEntity updatedEstudiante = estudianteService.updateEstudiante(estudianteOld.getId(), estudiante);
         assertNotNull(updatedEstudiante);
 
         EstudianteEntity estudianteBD = entityManager.find(EstudianteEntity.class,updatedEstudiante.getId());
         assertEquals(updatedEstudiante.getId(), estudianteBD.getId());
         assertEquals(updatedEstudiante.getNombre(), estudianteBD.getNombre());
         assertEquals(updatedEstudiante.getLogin(), estudianteBD.getLogin());
     }
    
    @Test
    void updateEstudianteInvalido() {
        assertThrows(EntityNotFoundException.class, () -> {
            EstudianteEntity estudiante = factory.manufacturePojo(EstudianteEntity.class);
            EstudianteEntity estudianteOld = estudiantes.get(0);
            estudianteOld.setId(0L);
            estudianteService.updateEstudiante(estudianteOld.getId(), estudiante);
        });
    }

    @Test
    void updateEstudianteNombreInvalido() {
        assertThrows(IllegalOperationException.class, () -> {
            EstudianteEntity estudiante = factory.manufacturePojo(EstudianteEntity.class);
            EstudianteEntity estudianteOld = estudiantes.get(0);
            estudiante.setNombre(null);
            estudianteService.updateEstudiante(estudianteOld.getId(), estudiante);
        }  );
    }

    @Test
    void updateEstudianteNombreInvalido1() {
        assertThrows(IllegalOperationException.class, () -> {
            EstudianteEntity estudiante = factory.manufacturePojo(EstudianteEntity.class);
            EstudianteEntity estudianteOld = estudiantes.get(0);
            estudiante.setNombre("");
            estudianteService.updateEstudiante(estudianteOld.getId(), estudiante);
        }  );
    }

    @Test
    void updateEstudianteLoginInvalido() {
        assertThrows(IllegalOperationException.class, () -> {
            EstudianteEntity estudiante = factory.manufacturePojo(EstudianteEntity.class);
            EstudianteEntity estudianteOld = estudiantes.get(0);
            estudiante.setLogin(null);
            estudianteService.updateEstudiante(estudianteOld.getId(), estudiante);
        }  );
    }

    @Test
    void updateEstudianteLoginInvalido1() {
        assertThrows(IllegalOperationException.class, () -> {
            EstudianteEntity estudiante = factory.manufacturePojo(EstudianteEntity.class);
            EstudianteEntity estudianteOld = estudiantes.get(0);
            estudiante.setLogin("");
            estudianteService.updateEstudiante(estudianteOld.getId(), estudiante);
        }  );
    }

    /**
     * Pruebas para eliminar estudiantes
     * @throws IllegalOperationException
     * @throws EntityNotFoundException
     */
    
    @Test 
    void deleteEstudiante() throws IllegalOperationException , EntityNotFoundException {
        EstudianteEntity estudiante = estudiantes.get(0);
        estudianteService.deleteEstudiante(estudiante.getId());
		assertTrue(!estudianteService.getEstudiantes().contains(estudiante));
    }

    @Test
    void deleteEstudianteInvalido() {
        assertThrows(EntityNotFoundException.class, () -> {
            EstudianteEntity estudiante = estudiantes.get(0);
            estudiante.setId(0L);
            estudianteService.deleteEstudiante(estudiante.getId());
        }  );
    }

    @Test
    void deleteEstudianteGrupoInvalido() {
        assertThrows(IllegalOperationException.class, () -> {
            EstudianteEntity estudiante = estudiantes.get(0);
            estudiante.setGrupo(grupo);
            estudianteService.deleteEstudiante(estudiante.getId());
        }  );
    }

    @Test
    void deleteEstudianteEvaluacionesHechasInvalido() {
        assertThrows(IllegalOperationException.class, () -> {
            EstudianteEntity estudiante = estudiantes.get(0);
            estudiante.setEvaluacionesHechas(evaluacionesHechas);
            estudianteService.deleteEstudiante(estudiante.getId());
        }  );
    }

    @Test
    void deleteEstudianteEvaluacionesRecibidasInvalido() {
        assertThrows(IllegalOperationException.class, () -> {
            EstudianteEntity estudiante = estudiantes.get(0);
            estudiante.setEvaluacionesRecibidas(evaluacionesRecibidas);
            estudianteService.deleteEstudiante(estudiante.getId());
        }  );
    }




}

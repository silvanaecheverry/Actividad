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
import com.dsw.students_grades.entities.CoEvaluacionEntity;
import com.dsw.students_grades.entities.GrupoEntity;
import com.dsw.students_grades.exceptions.EntityNotFoundException;
import com.dsw.students_grades.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import jakarta.transaction.Transactional;

@DataJpaTest
@Transactional
@Import(CoEvaluacionService.class)
public class CoEvaluacionServiceTest {

    @Autowired
    private CoEvaluacionService coEvaluacionService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private EstudianteEntity estudiante1;
    private EstudianteEntity estudiante2;
    private List<CoEvaluacionEntity> coEvaluaciones = new ArrayList<>();

    private GrupoEntity grupo;

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from EstudianteEntity");
        entityManager.getEntityManager().createQuery("delete from CoEvaluacionEntity");
        entityManager.getEntityManager().createQuery("delete from GrupoEntity");
    }

    private void insertData(){
        for (int i = 0 ; i < 3; i++){
            
            CoEvaluacionEntity coEvaluacion = factory.manufacturePojo(CoEvaluacionEntity.class);
            entityManager.persist(coEvaluacion);
            coEvaluaciones.add(coEvaluacion);
        }

        estudiante1 = factory.manufacturePojo(EstudianteEntity.class);
        estudiante2 = factory.manufacturePojo(EstudianteEntity.class);
        entityManager.persist(estudiante1);
        entityManager.persist(estudiante2);

    }

    /**
     * Pruebas para crear coEvaluaciones
     * @throws IllegalOperationException
     */
    @Test
    void createCoEvaluacion() throws IllegalOperationException {
        CoEvaluacionEntity coEvaluacion = factory.manufacturePojo(CoEvaluacionEntity.class);
        CoEvaluacionEntity newCoEvaluacion = coEvaluacionService.createCoEvaluacion(coEvaluacion);
        assertNotNull(newCoEvaluacion);

        CoEvaluacionEntity coEvaluacionBD = entityManager.find(CoEvaluacionEntity.class,newCoEvaluacion.getId());
        assertEquals(newCoEvaluacion.getId(), coEvaluacionBD.getId());
        assertEquals(newCoEvaluacion.getFecha(), coEvaluacionBD.getFecha());
        assertEquals(newCoEvaluacion.getCalificacion(), coEvaluacionBD.getCalificacion());
        assertEquals(newCoEvaluacion.getComentario(), coEvaluacionBD.getComentario());
    }

    @Test
    void createCoEvaluacionComentarioInvalido() {
        assertThrows(IllegalOperationException.class, () -> {
            CoEvaluacionEntity coEvaluacion = factory.manufacturePojo(CoEvaluacionEntity.class);
            coEvaluacion.setComentario(null);
            coEvaluacionService.createCoEvaluacion(coEvaluacion);
        }  );
    }

    @Test
    void createCoEvaluacionFechaInvalida() {
        assertThrows(IllegalOperationException.class, () -> {
            CoEvaluacionEntity coEvaluacion = factory.manufacturePojo(CoEvaluacionEntity.class);
            coEvaluacion.setFecha(null);
            coEvaluacionService.createCoEvaluacion(coEvaluacion);
        }  );
    }

    @Test
    void createCoEvaluacionComentarioInvalido1() {
        assertThrows(IllegalOperationException.class, () -> {
            CoEvaluacionEntity coEvaluacion = factory.manufacturePojo(CoEvaluacionEntity.class);
            coEvaluacion.setComentario("");
            coEvaluacionService.createCoEvaluacion(coEvaluacion);
        } );
    }

    @Test
    void createCoEvaluacionCalificacionInvalida() {
        assertThrows(IllegalOperationException.class, () -> {
            CoEvaluacionEntity coEvaluacion = factory.manufacturePojo(CoEvaluacionEntity.class);
            coEvaluacion.setCalificacion(null);
            coEvaluacionService.createCoEvaluacion(coEvaluacion);
        } );
    }

    @Test
    void createCoEvaluacionCalificacionInvalida1() {
        assertThrows(IllegalOperationException.class, () -> {
            CoEvaluacionEntity coEvaluacion = factory.manufacturePojo(CoEvaluacionEntity.class);
            coEvaluacion.setCalificacion(-1.0);
            coEvaluacionService.createCoEvaluacion(coEvaluacion);
        } );
    }

    @Test
    void createCoEvaluacionCalificacionInvalida2() {
        assertThrows(IllegalOperationException.class, () -> {
            CoEvaluacionEntity coEvaluacion = factory.manufacturePojo(CoEvaluacionEntity.class);
            coEvaluacion.setCalificacion(5.1);
            coEvaluacionService.createCoEvaluacion(coEvaluacion);
        } );
    }

    /**
     * Pruebas para obtener todos los coEvaluaciones
     */
    @Test
    void getCoEvaluaciones(){
        List<CoEvaluacionEntity> list = coEvaluacionService.getCoEvaluaciones();
		assertEquals(coEvaluaciones.size(), list.size());
		for (CoEvaluacionEntity entity : list) {
			boolean found = false;
			for (CoEvaluacionEntity storedEntity : coEvaluaciones) {
				if (entity.getId().equals(storedEntity.getId())) {
					found = true;
				}
			}
			assertTrue(found);
		}
    }

    /**
     * Pruebas para obtener un coEvaluacion con un id especifico
     * @throws EntityNotFoundException
     */
    @Test
    void getCoEvaluacion() throws EntityNotFoundException {
        CoEvaluacionEntity coEvaluacion = coEvaluaciones.get(0);
        CoEvaluacionEntity coEvaluacionBD = coEvaluacionService.getCoEvaluacion(coEvaluacion.getId());

        assertEquals(coEvaluacion.getId(), coEvaluacionBD.getId());
        assertEquals(coEvaluacion.getFecha(), coEvaluacionBD.getFecha());
        assertEquals(coEvaluacion.getCalificacion(), coEvaluacionBD.getCalificacion());
        assertEquals(coEvaluacion.getComentario(), coEvaluacionBD.getComentario());

    }
    
    @Test
    void getCoEvaluacionInvalido() {
        assertThrows(EntityNotFoundException.class, () -> {
            CoEvaluacionEntity coEvaluacion = coEvaluaciones.get(0);
            coEvaluacion.setId(0L);
            coEvaluacionService.getCoEvaluacion(coEvaluacion.getId());
        });
    }

    /**
     * Pruebas para actualizar coEvaluaciones
     * @throws IllegalOperationException
     * @throws EntityNotFoundException
     */

     @Test
     void updateCoEvaluacion() throws IllegalOperationException , EntityNotFoundException {
         CoEvaluacionEntity coEvaluacion = factory.manufacturePojo(CoEvaluacionEntity.class);
         CoEvaluacionEntity coEvaluacionOld = coEvaluaciones.get(0);
         CoEvaluacionEntity updatedCoEvaluacion = coEvaluacionService.updateCoEvaluacion(coEvaluacionOld.getId(), coEvaluacion);
         assertNotNull(updatedCoEvaluacion);
 
         CoEvaluacionEntity coEvaluacionBD = entityManager.find(CoEvaluacionEntity.class,updatedCoEvaluacion.getId());
         assertEquals(updatedCoEvaluacion.getId(), coEvaluacionBD.getId());
         assertEquals(updatedCoEvaluacion.getFecha(), coEvaluacionBD.getFecha());
         assertEquals(updatedCoEvaluacion.getCalificacion(), coEvaluacionBD.getCalificacion());
         assertEquals(updatedCoEvaluacion.getComentario(), coEvaluacionBD.getComentario());
     }
    
    @Test
    void updateCoEvaluacionInvalido() {
        assertThrows(EntityNotFoundException.class, () -> {
            CoEvaluacionEntity coEvaluacion = factory.manufacturePojo(CoEvaluacionEntity.class);
            CoEvaluacionEntity coEvaluacionOld = coEvaluaciones.get(0);
            coEvaluacionOld.setId(0L);
            coEvaluacionService.updateCoEvaluacion(coEvaluacionOld.getId(), coEvaluacion);
        });
    }

    @Test
    void updateCoEvaluacionComentarioInvalido() {
        assertThrows(IllegalOperationException.class, () -> {
            CoEvaluacionEntity coEvaluacion = factory.manufacturePojo(CoEvaluacionEntity.class);
            CoEvaluacionEntity coEvaluacionOld = coEvaluaciones.get(0);
            coEvaluacion.setComentario(null);
            coEvaluacionService.updateCoEvaluacion(coEvaluacionOld.getId(), coEvaluacion);
        }  );
    }

    @Test
    void updateCoEvaluacionComentarioInvalido1() {
        assertThrows(IllegalOperationException.class, () -> {
            CoEvaluacionEntity coEvaluacion = factory.manufacturePojo(CoEvaluacionEntity.class);
            CoEvaluacionEntity coEvaluacionOld = coEvaluaciones.get(0);
            coEvaluacion.setComentario("");
            coEvaluacionService.updateCoEvaluacion(coEvaluacionOld.getId(), coEvaluacion);
        }  );
    }

    @Test
    void updateCoEvaluacionCalificacionInvalida() {
        assertThrows(IllegalOperationException.class, () -> {
            CoEvaluacionEntity coEvaluacion = factory.manufacturePojo(CoEvaluacionEntity.class);
            CoEvaluacionEntity coEvaluacionOld = coEvaluaciones.get(0);
            coEvaluacion.setCalificacion(null);
            coEvaluacionService.updateCoEvaluacion(coEvaluacionOld.getId(), coEvaluacion);
        }  );
    }
    @Test
    void updateCoEvaluacionCalificacionInvalida1() {
        assertThrows(IllegalOperationException.class, () -> {
            CoEvaluacionEntity coEvaluacion = factory.manufacturePojo(CoEvaluacionEntity.class);
            CoEvaluacionEntity coEvaluacionOld = coEvaluaciones.get(0);
            coEvaluacion.setCalificacion(-1.0);
            coEvaluacionService.updateCoEvaluacion(coEvaluacionOld.getId(), coEvaluacion);
        }  );
    }

    @Test
    void updateCoEvaluacionCalificacionInvalida2() {
        assertThrows(IllegalOperationException.class, () -> {
            CoEvaluacionEntity coEvaluacion = factory.manufacturePojo(CoEvaluacionEntity.class);
            CoEvaluacionEntity coEvaluacionOld = coEvaluaciones.get(0);
            coEvaluacion.setCalificacion(5.1);
            coEvaluacionService.updateCoEvaluacion(coEvaluacionOld.getId(), coEvaluacion);
        }  );
    }

    @Test
    void updateCoEvaluacionFechaInvalida() {
        assertThrows(IllegalOperationException.class, () -> {
            CoEvaluacionEntity coEvaluacion = factory.manufacturePojo(CoEvaluacionEntity.class);
            CoEvaluacionEntity coEvaluacionOld = coEvaluaciones.get(0);
            coEvaluacion.setFecha(null);
            coEvaluacionService.updateCoEvaluacion(coEvaluacionOld.getId(), coEvaluacion);
        }  );
    }

    /**
     * Pruebas para eliminar coEvaluaciones
     * @throws IllegalOperationException
     * @throws EntityNotFoundException
     */
    
    @Test 
    void deleteCoEvaluacion() throws IllegalOperationException , EntityNotFoundException {
        CoEvaluacionEntity coEvaluacion = coEvaluaciones.get(0);
        coEvaluacionService.deleteCoEvaluacion(coEvaluacion.getId());
		assertTrue(!coEvaluacionService.getCoEvaluaciones().contains(coEvaluacion));
    }

    @Test
    void deleteCoEvaluacionInvalido() {
        assertThrows(EntityNotFoundException.class, () -> {
            CoEvaluacionEntity coEvaluacion = coEvaluaciones.get(0);
            coEvaluacion.setId(0L);
            coEvaluacionService.deleteCoEvaluacion(coEvaluacion.getId());
        }  );
    }

    @Test
    void deleteCoEvaluacionEstudianteCalificadorInvalido() {
        assertThrows(IllegalOperationException.class, () -> {
            CoEvaluacionEntity coEvaluacion = coEvaluaciones.get(0);
            coEvaluacion.setEstudianteCalificador(estudiante1);
            coEvaluacionService.deleteCoEvaluacion(coEvaluacion.getId());
        }  );
    }

    @Test
    void deleteCoEvaluacionEstudianteCalificadoInvalido() {
        assertThrows(IllegalOperationException.class, () -> {
            CoEvaluacionEntity coEvaluacion = coEvaluaciones.get(0);
            coEvaluacion.setEstudianteCalificado(estudiante1);
            coEvaluacionService.deleteCoEvaluacion(coEvaluacion.getId());
        }  );
    }

}

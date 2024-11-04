package com.dsw.students_grades.services;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.dsw.students_grades.exceptions.EntityNotFoundException;
import com.dsw.students_grades.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import jakarta.transaction.Transactional;

@DataJpaTest
@Transactional
@Import(EstudianteCalificadoCoevaluacionService.class)
public class EstudianteCalificadoCoevaluacionServiceTest {

    @Autowired
    EstudianteCalificadoCoevaluacionService estudianteCalificadoCoEvaluacionService;

    @Autowired
    TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private EstudianteEntity estudianteCalificado;
    private List<CoEvaluacionEntity> coEvaluaciones = new ArrayList<>();

    @BeforeEach
	void setUp() {
		clearData();
		insertData();
	}

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from CoEvaluacionEntity");
        entityManager.getEntityManager().createQuery("delete from CoEvaluacionEntity");
        entityManager.getEntityManager().createQuery("delete from EstudianteEntity");
    }

    private void insertData(){
        for(int i=0; i<3;i++){
            CoEvaluacionEntity e = factory.manufacturePojo(CoEvaluacionEntity.class);
            entityManager.persist(e);
            coEvaluaciones.add(e);
        }
        estudianteCalificado = factory.manufacturePojo(EstudianteEntity.class);
        entityManager.persist(estudianteCalificado);
        estudianteCalificado.setEvaluacionesRecibidas(coEvaluaciones);
    }

    /**
     * Tests de añadir coEvaluacion
     */

    @Test
    void addCoEvaluacionTest() throws EntityNotFoundException{
        CoEvaluacionEntity e = factory.manufacturePojo(CoEvaluacionEntity.class);
        entityManager.persist(e);

        EstudianteEntity g = estudianteCalificadoCoEvaluacionService.addCoEvaluacion(estudianteCalificado.getId(), e.getId());

        assertEquals(g.getId(),estudianteCalificado.getId());
        assertEquals(g.getEvaluacionesRecibidas().size(),estudianteCalificado.getEvaluacionesRecibidas().size());
        assertTrue(estudianteCalificado.getEvaluacionesRecibidas().contains(e));
    }

    @Test
    void addCoEvaluacionYaAsociadoTest() throws EntityNotFoundException{
        CoEvaluacionEntity e = coEvaluaciones.get(0);

        EstudianteEntity g = estudianteCalificadoCoEvaluacionService.addCoEvaluacion(estudianteCalificado.getId(), e.getId());

        assertEquals(g.getId(),estudianteCalificado.getId());
        assertEquals(g.getEvaluacionesRecibidas().size(),estudianteCalificado.getEvaluacionesRecibidas().size());
        assertTrue(estudianteCalificado.getEvaluacionesRecibidas().contains(e));
    }

    @Test
    void addCoEvaluacionInexistenteTest() {
        assertThrows(EntityNotFoundException.class, () -> {
            estudianteCalificadoCoEvaluacionService.addCoEvaluacion(estudianteCalificado.getId(), 0L);
        });
    }

    @Test
    void addCoEvaluacionEstudianteInexistenteTest() {
        assertThrows(EntityNotFoundException.class, () -> {
            CoEvaluacionEntity e = factory.manufacturePojo(CoEvaluacionEntity.class);
            entityManager.persist(e);
            estudianteCalificadoCoEvaluacionService.addCoEvaluacion(0L, e.getId());
        });
    }

    /**
     * Tests de obtener coEvaluaciones
     */
     
    @Test
    void getEvaluacionesRecibidas() throws EntityNotFoundException{
        List<CoEvaluacionEntity> es = estudianteCalificadoCoEvaluacionService.getEvaluacionesRecibidas(estudianteCalificado.getId());
        assertEquals(es.size(), coEvaluaciones.size());
    }

    @Test
    void getEvaluacionesRecibidasEstudianteInexistente() {
        assertThrows(EntityNotFoundException.class, () -> {
            estudianteCalificadoCoEvaluacionService.getEvaluacionesRecibidas(0L);
        });
    }

    /**
     * Tests de obtener coEvaluacion especifico
     */
       
    @Test
    void getCoEvaluacion() throws EntityNotFoundException, IllegalOperationException{
        CoEvaluacionEntity e = coEvaluaciones.get(0);
        CoEvaluacionEntity e_f = estudianteCalificadoCoEvaluacionService.getCoEvaluacion(estudianteCalificado.getId(), e.getId());
        assertEquals(e.getId(), e_f.getId());
    }

    @Test
    void getCoEvaluacionEstudianteInexistente() {
         assertThrows(EntityNotFoundException.class, () -> {
            CoEvaluacionEntity e = coEvaluaciones.get(0);
            estudianteCalificadoCoEvaluacionService.getCoEvaluacion(0L, e.getId());
         });
    }

    @Test
    void getCoEvaluacionCoEvaluacionInexistente() {
         assertThrows(EntityNotFoundException.class, () -> {
            estudianteCalificadoCoEvaluacionService.getCoEvaluacion(estudianteCalificado.getId(),0L);
         });
    }

    @Test
    void getCoEvaluacionCoEvaluacionNoAsociado() {
         assertThrows(IllegalOperationException.class, () -> {
            CoEvaluacionEntity e = factory.manufacturePojo(CoEvaluacionEntity.class);
            entityManager.persist(e);
            estudianteCalificadoCoEvaluacionService.getCoEvaluacion(estudianteCalificado.getId(),e.getId());
         });
    }

    /*
     * Remover coEvaluaciones
     */

    @Test
    void removeCoEvaluacionTest() throws EntityNotFoundException{
        CoEvaluacionEntity e = coEvaluaciones.get(0);
        estudianteCalificadoCoEvaluacionService.removeCoEvaluacion(estudianteCalificado.getId(),e.getId());
        assertTrue(!estudianteCalificado.getEvaluacionesRecibidas().contains(e));
    }

    @Test
    void removeCoEvaluacionNoAsociadoTest() throws EntityNotFoundException{
        CoEvaluacionEntity e = factory.manufacturePojo(CoEvaluacionEntity.class);
        entityManager.persist(e);
        estudianteCalificadoCoEvaluacionService.removeCoEvaluacion(estudianteCalificado.getId(),e.getId());
        assertTrue(!estudianteCalificado.getEvaluacionesRecibidas().contains(e));
    }

    @Test
    void removeCoEvaluacionInexistenteTest() throws EntityNotFoundException{
        assertThrows(EntityNotFoundException.class, () -> {
            estudianteCalificadoCoEvaluacionService.removeCoEvaluacion(estudianteCalificado.getId(),0L);
         });
    }

    @Test
    void removeCoEvaluacionEstudianteInexistenteTest() throws EntityNotFoundException{
        assertThrows(EntityNotFoundException.class, () -> {
            CoEvaluacionEntity e = coEvaluaciones.get(0);
            estudianteCalificadoCoEvaluacionService.removeCoEvaluacion(0L,e.getId());
         });
    }
    
}

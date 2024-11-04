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
@Import(EstudianteCalificadorCoevaluacionService.class)
public class EstudianteCalificadorCoevaluacionServiceTest {

    @Autowired
    EstudianteCalificadorCoevaluacionService estudianteCalificadorCoEvaluacionService;

    @Autowired
    TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private EstudianteEntity estudianteCalificador;
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
        estudianteCalificador = factory.manufacturePojo(EstudianteEntity.class);
        entityManager.persist(estudianteCalificador);
        estudianteCalificador.setEvaluacionesHechas(coEvaluaciones);
    }

    /**
     * Tests de añadir coEvaluacion
     */

    @Test
    void addCoEvaluacionTest() throws EntityNotFoundException{
        CoEvaluacionEntity e = factory.manufacturePojo(CoEvaluacionEntity.class);
        entityManager.persist(e);

        EstudianteEntity g = estudianteCalificadorCoEvaluacionService.addCoEvaluacion(estudianteCalificador.getId(), e.getId());

        assertEquals(g.getId(),estudianteCalificador.getId());
        assertEquals(g.getEvaluacionesHechas().size(),estudianteCalificador.getEvaluacionesHechas().size());
        assertTrue(estudianteCalificador.getEvaluacionesHechas().contains(e));
    }

    @Test
    void addCoEvaluacionYaAsociadoTest() throws EntityNotFoundException{
        CoEvaluacionEntity e = coEvaluaciones.get(0);

        EstudianteEntity g = estudianteCalificadorCoEvaluacionService.addCoEvaluacion(estudianteCalificador.getId(), e.getId());

        assertEquals(g.getId(),estudianteCalificador.getId());
        assertEquals(g.getEvaluacionesHechas().size(),estudianteCalificador.getEvaluacionesHechas().size());
        assertTrue(estudianteCalificador.getEvaluacionesHechas().contains(e));
    }

    @Test
    void addCoEvaluacionInexistenteTest() {
        assertThrows(EntityNotFoundException.class, () -> {
            estudianteCalificadorCoEvaluacionService.addCoEvaluacion(estudianteCalificador.getId(), 0L);
        });
    }

    @Test
    void addCoEvaluacionEstudianteInexistenteTest() {
        assertThrows(EntityNotFoundException.class, () -> {
            CoEvaluacionEntity e = factory.manufacturePojo(CoEvaluacionEntity.class);
            entityManager.persist(e);
            estudianteCalificadorCoEvaluacionService.addCoEvaluacion(0L, e.getId());
        });
    }

    /**
     * Tests de obtener coEvaluaciones
     */
     
    @Test
    void getEvaluacionesHechas() throws EntityNotFoundException{
        List<CoEvaluacionEntity> es = estudianteCalificadorCoEvaluacionService.getEvaluacionesHechas(estudianteCalificador.getId());
        assertEquals(es.size(), coEvaluaciones.size());
    }

    @Test
    void getEvaluacionesHechasEstudianteInexistente() {
        assertThrows(EntityNotFoundException.class, () -> {
            estudianteCalificadorCoEvaluacionService.getEvaluacionesHechas(0L);
        });
    }

    /**
     * Tests de obtener coEvaluacion especifico
     */
       
    @Test
    void getCoEvaluacion() throws EntityNotFoundException, IllegalOperationException{
        CoEvaluacionEntity e = coEvaluaciones.get(0);
        CoEvaluacionEntity e_f = estudianteCalificadorCoEvaluacionService.getCoEvaluacion(estudianteCalificador.getId(), e.getId());
        assertEquals(e.getId(), e_f.getId());
    }

    @Test
    void getCoEvaluacionEstudianteInexistente() {
         assertThrows(EntityNotFoundException.class, () -> {
            CoEvaluacionEntity e = coEvaluaciones.get(0);
            estudianteCalificadorCoEvaluacionService.getCoEvaluacion(0L, e.getId());
         });
    }

    @Test
    void getCoEvaluacionCoEvaluacionInexistente() {
         assertThrows(EntityNotFoundException.class, () -> {
            estudianteCalificadorCoEvaluacionService.getCoEvaluacion(estudianteCalificador.getId(),0L);
         });
    }

    @Test
    void getCoEvaluacionCoEvaluacionNoAsociado() {
         assertThrows(IllegalOperationException.class, () -> {
            CoEvaluacionEntity e = factory.manufacturePojo(CoEvaluacionEntity.class);
            entityManager.persist(e);
            estudianteCalificadorCoEvaluacionService.getCoEvaluacion(estudianteCalificador.getId(),e.getId());
         });
    }

    /*
     * Remover coEvaluaciones
     */

    @Test
    void removeCoEvaluacionTest() throws EntityNotFoundException{
        CoEvaluacionEntity e = coEvaluaciones.get(0);
        estudianteCalificadorCoEvaluacionService.removeCoEvaluacion(estudianteCalificador.getId(),e.getId());
        assertTrue(!estudianteCalificador.getEvaluacionesHechas().contains(e));
    }

    @Test
    void removeCoEvaluacionNoAsociadoTest() throws EntityNotFoundException{
        CoEvaluacionEntity e = factory.manufacturePojo(CoEvaluacionEntity.class);
        entityManager.persist(e);
        estudianteCalificadorCoEvaluacionService.removeCoEvaluacion(estudianteCalificador.getId(),e.getId());
        assertTrue(!estudianteCalificador.getEvaluacionesHechas().contains(e));
    }

    @Test
    void removeCoEvaluacionInexistenteTest() throws EntityNotFoundException{
        assertThrows(EntityNotFoundException.class, () -> {
            estudianteCalificadorCoEvaluacionService.removeCoEvaluacion(estudianteCalificador.getId(),0L);
         });
    }

    @Test
    void removeCoEvaluacionEstudianteInexistenteTest() throws EntityNotFoundException{
        assertThrows(EntityNotFoundException.class, () -> {
            CoEvaluacionEntity e = coEvaluaciones.get(0);
            estudianteCalificadorCoEvaluacionService.removeCoEvaluacion(0L,e.getId());
         });
    }
    
}

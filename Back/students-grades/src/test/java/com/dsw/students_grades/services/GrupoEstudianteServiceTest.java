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

import com.dsw.students_grades.entities.EstudianteEntity;
import com.dsw.students_grades.entities.GrupoEntity;
import com.dsw.students_grades.exceptions.EntityNotFoundException;
import com.dsw.students_grades.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import jakarta.transaction.Transactional;

@DataJpaTest
@Transactional
@Import(GrupoEstudianteService.class)
public class GrupoEstudianteServiceTest {

    @Autowired
    GrupoEstudianteService grupoEstudianteService;

    @Autowired
    TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private GrupoEntity grupo;
    private List<EstudianteEntity> estudiantes = new ArrayList<>();

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
        for(int i=0; i<3;i++){
            EstudianteEntity e = factory.manufacturePojo(EstudianteEntity.class);
            entityManager.persist(e);
            estudiantes.add(e);
        }
        grupo = factory.manufacturePojo(GrupoEntity.class);
        entityManager.persist(grupo);
        grupo.setEstudiantes(estudiantes);
    }

    /**
     * Tests de añadir estudiante
     */

    @Test
    void addEstudianteTest() throws EntityNotFoundException{
        EstudianteEntity e = factory.manufacturePojo(EstudianteEntity.class);
        entityManager.persist(e);

        GrupoEntity g = grupoEstudianteService.addEstudiante(grupo.getId(), e.getId());

        assertEquals(g.getId(),grupo.getId());
        assertEquals(g.getEstudiantes().size(),grupo.getEstudiantes().size());
        assertTrue(grupo.getEstudiantes().contains(e));
    }

    @Test
    void addEstudianteYaAsociadoTest() throws EntityNotFoundException{
        EstudianteEntity e = estudiantes.get(0);

        GrupoEntity g = grupoEstudianteService.addEstudiante(grupo.getId(), e.getId());

        assertEquals(g.getId(),grupo.getId());
        assertEquals(g.getEstudiantes().size(),grupo.getEstudiantes().size());
        assertTrue(grupo.getEstudiantes().contains(e));
    }

    @Test
    void addEstudianteInexistenteTest() {
        assertThrows(EntityNotFoundException.class, () -> {
            grupoEstudianteService.addEstudiante(grupo.getId(), 0L);
        });
    }

    @Test
    void addEstudianteGrupoInexistenteTest() {
        assertThrows(EntityNotFoundException.class, () -> {
            EstudianteEntity e = factory.manufacturePojo(EstudianteEntity.class);
            entityManager.persist(e);
            grupoEstudianteService.addEstudiante(0L, e.getId());
        });
    }

    /**
     * Tests de obtener estudiantes
     */
     
    @Test
    void getEstudiantes() throws EntityNotFoundException{
        List<EstudianteEntity> es = grupoEstudianteService.getEstudiantes(grupo.getId());
        assertEquals(es.size(), estudiantes.size());
    }

    @Test
    void getEstudiantesGrupoInexistente() {
        assertThrows(EntityNotFoundException.class, () -> {
            grupoEstudianteService.getEstudiantes(0L);
        });
    }

    /**
     * Tests de obtener estudiante especifico
     */
       
    @Test
    void getEstudiante() throws EntityNotFoundException, IllegalOperationException{
        EstudianteEntity e = estudiantes.get(0);
        EstudianteEntity e_f = grupoEstudianteService.getEstudiante(grupo.getId(), e.getId());
        assertEquals(e.getId(), e_f.getId());
    }

    @Test
    void getEstudianteGrupoInexistente() {
         assertThrows(EntityNotFoundException.class, () -> {
            EstudianteEntity e = estudiantes.get(0);
            grupoEstudianteService.getEstudiante(0L, e.getId());
         });
    }

    @Test
    void getEstudianteEstudianteInexistente() {
         assertThrows(EntityNotFoundException.class, () -> {
            grupoEstudianteService.getEstudiante(grupo.getId(),0L);
         });
    }

    @Test
    void getEstudianteEstudianteNoAsociado() {
         assertThrows(IllegalOperationException.class, () -> {
            EstudianteEntity e = factory.manufacturePojo(EstudianteEntity.class);
            entityManager.persist(e);
            grupoEstudianteService.getEstudiante(grupo.getId(),e.getId());
         });
    }

    /*
     * Remover estudiantes
     */

    @Test
    void removeEstudianteTest() throws EntityNotFoundException{
        EstudianteEntity e = estudiantes.get(0);
        grupoEstudianteService.removeEstudiante(grupo.getId(),e.getId());
        assertTrue(!grupo.getEstudiantes().contains(e));
    }

    @Test
    void removeEstudianteNoAsociadoTest() throws EntityNotFoundException{
        EstudianteEntity e = factory.manufacturePojo(EstudianteEntity.class);
        entityManager.persist(e);
        grupoEstudianteService.removeEstudiante(grupo.getId(),e.getId());
        assertTrue(!grupo.getEstudiantes().contains(e));
    }

    @Test
    void removeEstudianteInexistenteTest() throws EntityNotFoundException{
        assertThrows(EntityNotFoundException.class, () -> {
            grupoEstudianteService.removeEstudiante(grupo.getId(),0L);
         });
    }

    @Test
    void removeEstudianteGrupoInexistenteTest() throws EntityNotFoundException{
        assertThrows(EntityNotFoundException.class, () -> {
            EstudianteEntity e = estudiantes.get(0);
            grupoEstudianteService.removeEstudiante(0L,e.getId());
         });
    }

    


    
}

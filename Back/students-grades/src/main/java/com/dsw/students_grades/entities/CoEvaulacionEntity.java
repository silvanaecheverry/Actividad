package com.dsw.students_grades.entities;
import java.util.Date;


import jakarta.persistence.Entity;
import lombok.Data;
import com.dsw.students_grades.entities.BaseEntity;

@Data
@Entity
public class CoEvaulacionEntity extends BaseEntity { 
    private double calificacion; 
    private String comentario; 
    private Date fecha; 

}
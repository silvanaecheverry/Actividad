package com.dsw.students_grades.exceptions;

public class ErrorMessage {

  public static final String ESTUDIANTE_NOT_FOUND = "No se ha encontrado el estudiante con el id dado";

  private ErrorMessage() {
    throw new IllegalStateException("Utility class");

  }
 }

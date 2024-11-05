import { Component, OnInit } from '@angular/core';
import { EstudianteService } from '../../estudiante/services/estudiante.service';
import { EstudianteCalificadoEvaluacionService } from '../../estudiante/services/estudianteCalificadoEvaluacion.service';
import { Estudiante } from '../../estudiante/estudiante.model';

@Component({
  selector: 'app-misevaluaciones',
  templateUrl: './misevaluaciones.component.html',
  styleUrl: './misevaluaciones.component.css'
})
export class MisevaluacionesComponent implements OnInit{
  calificacion : number = 0;
  private estudianteService: EstudianteService; 
  private estudianteCalificadoEvaluacionService: EstudianteCalificadoEvaluacionService;

  constructor(
    estudianteService: EstudianteService,
    estudianteCalificadoEvaluacionService: EstudianteCalificadoEvaluacionService
  ) {
    this.estudianteService = estudianteService;
    this.estudianteCalificadoEvaluacionService = estudianteCalificadoEvaluacionService;
  }

    ngOnInit(){}


}

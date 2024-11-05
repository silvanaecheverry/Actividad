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
  calificacion!: number;
  comentarios!: string[];
  private estudianteService: EstudianteService; 
  private estudianteCalificadoEvaluacionService: EstudianteCalificadoEvaluacionService;

  constructor(
    estudianteService: EstudianteService,
    estudianteCalificadoEvaluacionService: EstudianteCalificadoEvaluacionService
  ) {
    this.estudianteService = estudianteService;
    this.estudianteCalificadoEvaluacionService = estudianteCalificadoEvaluacionService;
  }

  async ngOnInit(){
    const currentUser: Estudiante | null = this.estudianteService.getCurrentUser();
    this.calificacion = await this.estudianteCalificadoEvaluacionService.getAverage(currentUser);
    this.comentarios = await this.estudianteCalificadoEvaluacionService.getComments(currentUser);
  }
}

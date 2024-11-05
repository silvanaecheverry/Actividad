import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MisevaluacionesComponent } from './misevaluaciones/misevaluaciones.component';
import { EstudianteModule } from '../estudiante/estudiante.module';



@NgModule({
  declarations: [
    MisevaluacionesComponent
  ],
  imports: [
    CommonModule,
    EstudianteModule
  ]
})
export class CoevaluacionesModule { }

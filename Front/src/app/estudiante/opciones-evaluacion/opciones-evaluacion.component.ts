import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-opciones-evaluacion',
  templateUrl: './opciones-evaluacion.component.html',
  styleUrls: ['./opciones-evaluacion.component.css']
})
export class OpcionesEvaluacionComponent {

  constructor(private router: Router) {}

  goToMisEvaluaciones(): void {
    this.router.navigate(['/misEvaluaciones']); // Adjust the route as necessary
  }

  goToEvaluarCompaneros(): void {
    this.router.navigate(['/evaluarCompaneros']); // Adjust the route as necessary
  }
}
import { Component, OnInit } from '@angular/core';
import { EstudianteService } from '../services/estudiante.service';
import { Estudiante } from '../estudiante.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  login: string = '';
  constructor(
    private estudianteService: EstudianteService,
    private router: Router
  ) {}

  ngOnInit() {}

  onSubmit(): void {
    console.log('Submit button pressed. Login:', this.login);
    if (this.login) {
      this.estudianteService.identifyEstudiante(this.login).subscribe({
        next: (estudiante: Estudiante) => {
          console.log('Estudiante fetched:', estudiante);
          this.router.navigate(['/opciones']);
        },
        error: (err) => {
          console.error('Error fetching estudiante:', err);
        }
      });
    } else {
      console.error('Login field is empty');
    }
  }
}

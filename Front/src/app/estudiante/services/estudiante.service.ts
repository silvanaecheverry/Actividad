import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from 'rxjs';
import { Estudiante } from '../estudiante.model';
import { CoEvaluacion } from "../../coevaluaciones/coevaluacion.model";

@Injectable({
    providedIn: 'root'
})
export class EstudianteService {
    private currentUser: Estudiante | null = null;

    private baseUrl = 'http://localhost:8080/api/estudiantes';

    constructor(private http: HttpClient) {}

    identifyEstudiante(login:string): Observable<Estudiante>{
        const url: string = `${this.baseUrl}/login/${login}`;
        return this.http.get<Estudiante>(url);
    }

    getCurrentUser(): Estudiante | null {
        return this.currentUser;
    }

}

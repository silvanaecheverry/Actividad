import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { firstValueFrom, Observable } from 'rxjs';
import { Estudiante } from '../estudiante.model';
import { CoEvaluacion } from "../../coevaluaciones/coevaluacion.model";

@Injectable({
    providedIn: 'root'
})
export class EstudianteCalificadoEvaluacionService {
    private baseUrl = 'http://localhost:8080/api/estudianteCalificado/'
    constructor(private http: HttpClient) {}

    getEvaluacionesRecibidas(e: Estudiante): Observable<CoEvaluacion[]>{
        const url = `${this.baseUrl}${e.id}/coEvaluaciones`;
        return this.http.get<CoEvaluacion[]>(url);
    }

    async getAverage(e: Estudiante | null): Promise<number> {
        if(e === null) return 0;
        let list : CoEvaluacion[] = await firstValueFrom(this.getEvaluacionesRecibidas(e));
        if(list.length === 0) return 0;
        let s: number = 0;
        for(let coev of list) s+= coev.calificacion;
        return s/=list.length;
    }

    async getComments(e: Estudiante | null):Promise<string[]> {
        if(e === null) return [];
        let coevs : CoEvaluacion[] = await firstValueFrom(this.getEvaluacionesRecibidas(e));
        let list : string[] = [];
        for(let coev of coevs){
            list.push(coev.comentario);
        }
        return list;
    }
}

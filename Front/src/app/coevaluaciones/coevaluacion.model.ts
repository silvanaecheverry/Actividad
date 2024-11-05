import {Estudiante} from '../estudiante/estudiante.model'

export class CoEvaluacion{
    constructor(
        public comentario: string,
        public fecha: Date,
        public calificacion: number,
        public estudianteCalificador: Estudiante,
        public estudianteCalificado: Estudiante
    ){}
}
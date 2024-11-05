import { Estudiante } from "../estudiante/estudiante.model";

export class Grupo {
    constructor(
        public id: number,
        public numero: number,
        public nombre: StaticRangeInit,
        public estudiantes: Estudiante[]
    ){}
}
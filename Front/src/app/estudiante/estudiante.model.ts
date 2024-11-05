import { CoEvaluacion } from '../coevaluaciones/coevaluacion.model';
import {Grupo} from '../grupos/grupo.model'

export class Estudiante {
    constructor(
        public id: number,
        public login: string,
        public nombre: string,
        public grupo: Grupo,
        public evaluacionesHechas: CoEvaluacion[],
        public evaluacionesRecibidas: CoEvaluacion[]
    ){}
}
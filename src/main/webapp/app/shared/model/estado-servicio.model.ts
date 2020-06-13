import { IContrato } from 'app/shared/model/contrato.model';

export interface IEstadoServicio {
  id?: number;
  nombre?: string;
  contratoes?: IContrato[];
}

export class EstadoServicio implements IEstadoServicio {
  constructor(public id?: number, public nombre?: string, public contratoes?: IContrato[]) {}
}

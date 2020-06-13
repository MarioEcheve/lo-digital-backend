import { IContrato } from 'app/shared/model/contrato.model';

export interface IModalidad {
  id?: number;
  nombre?: string;
  contratoes?: IContrato[];
}

export class Modalidad implements IModalidad {
  constructor(public id?: number, public nombre?: string, public contratoes?: IContrato[]) {}
}

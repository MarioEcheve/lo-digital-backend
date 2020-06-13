import { IContrato } from 'app/shared/model/contrato.model';

export interface ITipoMoneda {
  id?: number;
  nombre?: string;
  contratoes?: IContrato[];
}

export class TipoMoneda implements ITipoMoneda {
  constructor(public id?: number, public nombre?: string, public contratoes?: IContrato[]) {}
}

import { IContrato } from 'app/shared/model/contrato.model';

export interface ITipoMonto {
  id?: number;
  nombre?: string;
  contratoes?: IContrato[];
}

export class TipoMonto implements ITipoMonto {
  constructor(public id?: number, public nombre?: string, public contratoes?: IContrato[]) {}
}

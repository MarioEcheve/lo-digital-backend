import { IContrato } from 'app/shared/model/contrato.model';

export interface ITipoContrato {
  id?: number;
  nombre?: string;
  contratoes?: IContrato[];
}

export class TipoContrato implements ITipoContrato {
  constructor(public id?: number, public nombre?: string, public contratoes?: IContrato[]) {}
}

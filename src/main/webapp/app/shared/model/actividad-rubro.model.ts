import { IEntidad } from 'app/shared/model/entidad.model';

export interface IActividadRubro {
  id?: number;
  nombre?: string;
  entidads?: IEntidad[];
}

export class ActividadRubro implements IActividadRubro {
  constructor(public id?: number, public nombre?: string, public entidads?: IEntidad[]) {}
}

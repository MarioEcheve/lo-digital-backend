import { IContrato } from 'app/shared/model/contrato.model';
import { IEntidad } from 'app/shared/model/entidad.model';
import { IDependencia } from 'app/shared/model/dependencia.model';

export interface IComuna {
  id?: number;
  nombre?: string;
  contratoes?: IContrato[];
  entidads?: IEntidad[];
  dependencias?: IDependencia[];
}

export class Comuna implements IComuna {
  constructor(
    public id?: number,
    public nombre?: string,
    public contratoes?: IContrato[],
    public entidads?: IEntidad[],
    public dependencias?: IDependencia[]
  ) {}
}

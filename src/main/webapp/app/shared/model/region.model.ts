import { IEntidad } from 'app/shared/model/entidad.model';
import { IDependencia } from 'app/shared/model/dependencia.model';
import { IContrato } from 'app/shared/model/contrato.model';

export interface IRegion {
  id?: number;
  nombre?: string;
  entidads?: IEntidad[];
  dependencias?: IDependencia[];
  contratoes?: IContrato[];
}

export class Region implements IRegion {
  constructor(
    public id?: number,
    public nombre?: string,
    public entidads?: IEntidad[],
    public dependencias?: IDependencia[],
    public contratoes?: IContrato[]
  ) {}
}

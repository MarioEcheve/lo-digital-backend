import { IDependencia } from 'app/shared/model/dependencia.model';
import { IContrato } from 'app/shared/model/contrato.model';
import { IComuna } from 'app/shared/model/comuna.model';

export interface IRegion {
  id?: number;
  nombre?: string;
  dependencias?: IDependencia[];
  contratoes?: IContrato[];
  comunas?: IComuna[];
}

export class Region implements IRegion {
  constructor(
    public id?: number,
    public nombre?: string,
    public dependencias?: IDependencia[],
    public contratoes?: IContrato[],
    public comunas?: IComuna[]
  ) {}
}

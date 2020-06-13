import { Moment } from 'moment';
import { IUsuarioDependencia } from 'app/shared/model/usuario-dependencia.model';
import { IContrato } from 'app/shared/model/contrato.model';
import { IEntidad } from 'app/shared/model/entidad.model';
import { IRegion } from 'app/shared/model/region.model';
import { IComuna } from 'app/shared/model/comuna.model';

export interface IDependencia {
  id?: number;
  nombre?: string;
  direccion?: string;
  descripcion?: string;
  fechaCreacion?: Moment;
  fechaModificacion?: Moment;
  usuarioDependencias?: IUsuarioDependencia[];
  contratoes?: IContrato[];
  entidad?: IEntidad;
  region?: IRegion;
  comuna?: IComuna;
}

export class Dependencia implements IDependencia {
  constructor(
    public id?: number,
    public nombre?: string,
    public direccion?: string,
    public descripcion?: string,
    public fechaCreacion?: Moment,
    public fechaModificacion?: Moment,
    public usuarioDependencias?: IUsuarioDependencia[],
    public contratoes?: IContrato[],
    public entidad?: IEntidad,
    public region?: IRegion,
    public comuna?: IComuna
  ) {}
}

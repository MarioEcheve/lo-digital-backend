import { Moment } from 'moment';
import { IDependencia } from 'app/shared/model/dependencia.model';
import { IRegion } from 'app/shared/model/region.model';
import { ITipoEntidad } from 'app/shared/model/tipo-entidad.model';
import { IActividadRubro } from 'app/shared/model/actividad-rubro.model';
import { IComuna } from 'app/shared/model/comuna.model';

export interface IEntidad {
  id?: number;
  rut?: string;
  nombre?: string;
  direccion?: string;
  fechaCreacion?: Moment;
  fechaModificacion?: Moment;
  dependencias?: IDependencia[];
  region?: IRegion;
  tipoEntidad?: ITipoEntidad;
  actividadRubro?: IActividadRubro;
  comuna?: IComuna;
}

export class Entidad implements IEntidad {
  constructor(
    public id?: number,
    public rut?: string,
    public nombre?: string,
    public direccion?: string,
    public fechaCreacion?: Moment,
    public fechaModificacion?: Moment,
    public dependencias?: IDependencia[],
    public region?: IRegion,
    public tipoEntidad?: ITipoEntidad,
    public actividadRubro?: IActividadRubro,
    public comuna?: IComuna
  ) {}
}

import { Moment } from 'moment';
import { IDependencia } from 'app/shared/model/dependencia.model';
import { ITipoEntidad } from 'app/shared/model/tipo-entidad.model';
import { IActividadRubro } from 'app/shared/model/actividad-rubro.model';

export interface IEntidad {
  id?: number;
  rut?: string;
  nombre?: string;
  direccion?: string;
  fechaCreacion?: Moment;
  fechaModificacion?: Moment;
  dependencias?: IDependencia[];
  tipoEntidad?: ITipoEntidad;
  actividadRubro?: IActividadRubro;
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
    public tipoEntidad?: ITipoEntidad,
    public actividadRubro?: IActividadRubro
  ) {}
}

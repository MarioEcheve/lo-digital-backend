import { Moment } from 'moment';
import { IUsuarioDependencia } from 'app/shared/model/usuario-dependencia.model';

export interface IUsuario {
  id?: number;
  rut?: string;
  nombre?: string;
  apellidoPaterno?: string;
  apellidoMaterno?: string;
  profesionOficio?: string;
  emailPrincipal?: string;
  emailSecundario?: string;
  telefonoPrincipal?: string;
  telefonoSecundario?: string;
  fechaCreacion?: Moment;
  fechaModificacion?: Moment;
  usuarioDependencias?: IUsuarioDependencia[];
}

export class Usuario implements IUsuario {
  constructor(
    public id?: number,
    public rut?: string,
    public nombre?: string,
    public apellidoPaterno?: string,
    public apellidoMaterno?: string,
    public profesionOficio?: string,
    public emailPrincipal?: string,
    public emailSecundario?: string,
    public telefonoPrincipal?: string,
    public telefonoSecundario?: string,
    public fechaCreacion?: Moment,
    public fechaModificacion?: Moment,
    public usuarioDependencias?: IUsuarioDependencia[]
  ) {}
}

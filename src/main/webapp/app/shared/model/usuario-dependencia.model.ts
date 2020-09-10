import { Moment } from 'moment';
import { IUsuarioLibro } from 'app/shared/model/usuario-libro.model';
import { IUser } from 'app/core/user/user.model';
import { IDependencia } from 'app/shared/model/dependencia.model';
import { IPerfilUsuarioDependencia } from 'app/shared/model/perfil-usuario-dependencia.model';

export interface IUsuarioDependencia {
  id?: number;
  nombre?: string;
  rut?: string;
  fechaCreacion?: Moment;
  fechaModificacion?: Moment;
  estado?: boolean;
  usuarioLibros?: IUsuarioLibro[];
  usuario?: IUser;
  dependencia?: IDependencia;
  perfilUsuarioDependencia?: IPerfilUsuarioDependencia;
}

export class UsuarioDependencia implements IUsuarioDependencia {
  constructor(
    public id?: number,
    public nombre?: string,
    public rut?: string,
    public fechaCreacion?: Moment,
    public fechaModificacion?: Moment,
    public estado?: boolean,
    public usuarioLibros?: IUsuarioLibro[],
    public usuario?: IUser,
    public dependencia?: IDependencia,
    public perfilUsuarioDependencia?: IPerfilUsuarioDependencia
  ) {
    this.estado = this.estado || false;
  }
}

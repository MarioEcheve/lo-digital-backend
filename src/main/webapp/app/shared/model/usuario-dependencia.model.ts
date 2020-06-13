import { Moment } from 'moment';
import { IUsuarioLibro } from 'app/shared/model/usuario-libro.model';
import { IDependencia } from 'app/shared/model/dependencia.model';
import { IUsuario } from 'app/shared/model/usuario.model';
import { IPerfilUsuarioDependencia } from 'app/shared/model/perfil-usuario-dependencia.model';

export interface IUsuarioDependencia {
  id?: number;
  fechaCreacion?: Moment;
  fechaModificacion?: Moment;
  estado?: boolean;
  usuarioLibros?: IUsuarioLibro[];
  dependencia?: IDependencia;
  usuario?: IUsuario;
  perfilUsuarioDependencia?: IPerfilUsuarioDependencia;
}

export class UsuarioDependencia implements IUsuarioDependencia {
  constructor(
    public id?: number,
    public fechaCreacion?: Moment,
    public fechaModificacion?: Moment,
    public estado?: boolean,
    public usuarioLibros?: IUsuarioLibro[],
    public dependencia?: IDependencia,
    public usuario?: IUsuario,
    public perfilUsuarioDependencia?: IPerfilUsuarioDependencia
  ) {
    this.estado = this.estado || false;
  }
}

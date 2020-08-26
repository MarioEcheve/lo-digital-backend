import { Moment } from 'moment';
import { IGesAlerta } from 'app/shared/model/ges-alerta.model';
import { IGesNota } from 'app/shared/model/ges-nota.model';
import { IGesFavorito } from 'app/shared/model/ges-favorito.model';
import { ILibro } from 'app/shared/model/libro.model';
import { IUsuarioDependencia } from 'app/shared/model/usuario-dependencia.model';
import { IUsuarioLibroPerfil } from 'app/shared/model/usuario-libro-perfil.model';

export interface IUsuarioLibro {
  id?: number;
  estado?: boolean;
  cargoFuncion?: string;
  fechaCreacion?: Moment;
  fechaModificacion?: Moment;
  adminActivo?: boolean;
  gesAlertas?: IGesAlerta[];
  gesNotas?: IGesNota[];
  gesFavoritos?: IGesFavorito[];
  libro?: ILibro;
  usuarioDependencia?: IUsuarioDependencia;
  perfilUsuarioLibro?: IUsuarioLibroPerfil;
}

export class UsuarioLibro implements IUsuarioLibro {
  constructor(
    public id?: number,
    public estado?: boolean,
    public cargoFuncion?: string,
    public fechaCreacion?: Moment,
    public fechaModificacion?: Moment,
    public adminActivo?: boolean,
    public gesAlertas?: IGesAlerta[],
    public gesNotas?: IGesNota[],
    public gesFavoritos?: IGesFavorito[],
    public libro?: ILibro,
    public usuarioDependencia?: IUsuarioDependencia,
    public perfilUsuarioLibro?: IUsuarioLibroPerfil
  ) {
    this.estado = this.estado || false;
    this.adminActivo = this.adminActivo || false;
  }
}

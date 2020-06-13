import { IUsuarioLibro } from 'app/shared/model/usuario-libro.model';

export interface IUsuarioLibroPerfil {
  id?: number;
  nombre?: string;
  usuarioLibros?: IUsuarioLibro[];
}

export class UsuarioLibroPerfil implements IUsuarioLibroPerfil {
  constructor(public id?: number, public nombre?: string, public usuarioLibros?: IUsuarioLibro[]) {}
}

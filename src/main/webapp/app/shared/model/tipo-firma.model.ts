import { ILibro } from 'app/shared/model/libro.model';

export interface ITipoFirma {
  id?: number;
  nombre?: string;
  libros?: ILibro[];
}

export class TipoFirma implements ITipoFirma {
  constructor(public id?: number, public nombre?: string, public libros?: ILibro[]) {}
}

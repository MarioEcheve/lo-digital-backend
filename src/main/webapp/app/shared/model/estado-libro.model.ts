import { ILibro } from 'app/shared/model/libro.model';

export interface IEstadoLibro {
  id?: number;
  nombre?: string;
  libros?: ILibro[];
}

export class EstadoLibro implements IEstadoLibro {
  constructor(public id?: number, public nombre?: string, public libros?: ILibro[]) {}
}

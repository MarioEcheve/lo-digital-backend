import { IFolio } from 'app/shared/model/folio.model';

export interface IEstadoRespuesta {
  id?: number;
  nombre?: string;
  folios?: IFolio[];
}

export class EstadoRespuesta implements IEstadoRespuesta {
  constructor(public id?: number, public nombre?: string, public folios?: IFolio[]) {}
}

import { Moment } from 'moment';
import { IFolio } from 'app/shared/model/folio.model';
import { IUsuarioLibro } from 'app/shared/model/usuario-libro.model';

export interface IGesAlerta {
  id?: number;
  nota?: string;
  fechaAlerta?: Moment;
  fechaCreacion?: Moment;
  fechaModificacion?: Moment;
  folio?: IFolio;
  usuarioLibro?: IUsuarioLibro;
}

export class GesAlerta implements IGesAlerta {
  constructor(
    public id?: number,
    public nota?: string,
    public fechaAlerta?: Moment,
    public fechaCreacion?: Moment,
    public fechaModificacion?: Moment,
    public folio?: IFolio,
    public usuarioLibro?: IUsuarioLibro
  ) {}
}

export interface ITipoUsuarioDependencia {
  id?: number;
  nombre?: string;
}

export class TipoUsuarioDependencia implements ITipoUsuarioDependencia {
  constructor(public id?: number, public nombre?: string) {}
}

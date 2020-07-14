import { Moment } from 'moment';
import { ILibro } from 'app/shared/model/libro.model';
import { IDependencia } from 'app/shared/model/dependencia.model';
import { IRegion } from 'app/shared/model/region.model';
import { ITipoContrato } from 'app/shared/model/tipo-contrato.model';
import { IModalidad } from 'app/shared/model/modalidad.model';
import { IComuna } from 'app/shared/model/comuna.model';
import { ITipoMoneda } from 'app/shared/model/tipo-moneda.model';
import { ITipoMonto } from 'app/shared/model/tipo-monto.model';
import { IEstadoServicio } from 'app/shared/model/estado-servicio.model';

export interface IContrato {
  id?: number;
  fechaInicioServicio?: Moment;
  fechaTerminoServicio?: Moment;
  fechaTerminoAcceso?: Moment;
  observacionesServicio?: string;
  codigo?: string;
  nombre?: string;
  descripcion?: string;
  tipoOtro?: string;
  modalidadOtra?: string;
  direccion?: string;
  monto?: number;
  fechaInicio?: Moment;
  fechaTermino?: Moment;
  observaciones?: string;
  nombreContacto?: string;
  cargo?: string;
  telefonoContacto?: string;
  creaLibroAdminMan?: boolean;
  creaLibroAdminCon?: boolean;
  actualizarContratoAdminMan?: boolean;
  actualizarContratoAdminCon?: boolean;
  telefonoContactoSecundario?: string;
  emailContacto?: string;
  idDependenciaContratista?: number;
  libros?: ILibro[];
  dependenciaMandante?: IDependencia;
  region?: IRegion;
  tipoContrato?: ITipoContrato;
  modalidad?: IModalidad;
  comuna?: IComuna;
  tipoMoneda?: ITipoMoneda;
  tipoMonto?: ITipoMonto;
  estadoServicio?: IEstadoServicio;
}

export class Contrato implements IContrato {
  constructor(
    public id?: number,
    public fechaInicioServicio?: Moment,
    public fechaTerminoServicio?: Moment,
    public fechaTerminoAcceso?: Moment,
    public observacionesServicio?: string,
    public codigo?: string,
    public nombre?: string,
    public descripcion?: string,
    public tipoOtro?: string,
    public modalidadOtra?: string,
    public direccion?: string,
    public monto?: number,
    public fechaInicio?: Moment,
    public fechaTermino?: Moment,
    public observaciones?: string,
    public nombreContacto?: string,
    public cargo?: string,
    public telefonoContacto?: string,
    public creaLibroAdminMan?: boolean,
    public creaLibroAdminCon?: boolean,
    public actualizarContratoAdminMan?: boolean,
    public actualizarContratoAdminCon?: boolean,
    public telefonoContactoSecundario?: string,
    public emailContacto?: string,
    public idDependenciaContratista?: number,
    public libros?: ILibro[],
    public dependenciaMandante?: IDependencia,
    public region?: IRegion,
    public tipoContrato?: ITipoContrato,
    public modalidad?: IModalidad,
    public comuna?: IComuna,
    public tipoMoneda?: ITipoMoneda,
    public tipoMonto?: ITipoMonto,
    public estadoServicio?: IEstadoServicio
  ) {
    this.creaLibroAdminMan = this.creaLibroAdminMan || false;
    this.creaLibroAdminCon = this.creaLibroAdminCon || false;
    this.actualizarContratoAdminMan = this.actualizarContratoAdminMan || false;
    this.actualizarContratoAdminCon = this.actualizarContratoAdminCon || false;
  }
}

import { Moment } from 'moment';
import { IArchivo } from 'app/shared/model/archivo.model';
import { IGesAlerta } from 'app/shared/model/ges-alerta.model';
import { IGesNota } from 'app/shared/model/ges-nota.model';
import { IGesFavorito } from 'app/shared/model/ges-favorito.model';
import { IFolioReferencia } from 'app/shared/model/folio-referencia.model';
import { ILibro } from 'app/shared/model/libro.model';
import { ITipoFolio } from 'app/shared/model/tipo-folio.model';
import { IEstadoRespuesta } from 'app/shared/model/estado-respuesta.model';

export interface IFolio {
  id?: number;
  idUsuarioCreador?: number;
  idUsuarioFirma?: number;
  idUsuarioLectura?: number;
  numeroFolio?: number;
  requiereRespuesta?: boolean;
  fechaRequerida?: Moment;
  estadoLectura?: boolean;
  estadoFolio?: boolean;
  entidadCreacion?: boolean;
  fechaCreacion?: Moment;
  idlibroRelacionado?: number;
  idFolioRelacionado?: number;
  idFolioRespuesta?: number;
  fechaModificacion?: Moment;
  fechaFirma?: Moment;
  fechaLectura?: Moment;
  asunto?: string;
  anotacion?: string;
  pdfFirmadoContentType?: string;
  pdfFirmado?: any;
  pdfLecturaContentType?: string;
  pdfLectura?: any;
  idReceptor?: number;
  poseeFolioReferencia?: boolean;
  emisorMarcado?: string;
  tipoFolioMarcado?: string;
  archivos?: IArchivo[];
  gesAlertas?: IGesAlerta[];
  gesNotas?: IGesNota[];
  gesFavoritos?: IGesFavorito[];
  folioReferencias?: IFolioReferencia[];
  libro?: ILibro;
  tipoFolio?: ITipoFolio;
  estadoRespuesta?: IEstadoRespuesta;
}

export class Folio implements IFolio {
  constructor(
    public id?: number,
    public idUsuarioCreador?: number,
    public idUsuarioFirma?: number,
    public idUsuarioLectura?: number,
    public numeroFolio?: number,
    public requiereRespuesta?: boolean,
    public fechaRequerida?: Moment,
    public estadoLectura?: boolean,
    public estadoFolio?: boolean,
    public entidadCreacion?: boolean,
    public fechaCreacion?: Moment,
    public idlibroRelacionado?: number,
    public idFolioRelacionado?: number,
    public idFolioRespuesta?: number,
    public fechaModificacion?: Moment,
    public fechaFirma?: Moment,
    public fechaLectura?: Moment,
    public asunto?: string,
    public anotacion?: string,
    public pdfFirmadoContentType?: string,
    public pdfFirmado?: any,
    public pdfLecturaContentType?: string,
    public pdfLectura?: any,
    public idReceptor?: number,
    public poseeFolioReferencia?: boolean,
    public emisorMarcado?: string,
    public tipoFolioMarcado?: string,
    public archivos?: IArchivo[],
    public gesAlertas?: IGesAlerta[],
    public gesNotas?: IGesNota[],
    public gesFavoritos?: IGesFavorito[],
    public folioReferencias?: IFolioReferencia[],
    public libro?: ILibro,
    public tipoFolio?: ITipoFolio,
    public estadoRespuesta?: IEstadoRespuesta
  ) {
    this.requiereRespuesta = this.requiereRespuesta || false;
    this.estadoLectura = this.estadoLectura || false;
    this.estadoFolio = this.estadoFolio || false;
    this.entidadCreacion = this.entidadCreacion || false;
    this.poseeFolioReferencia = this.poseeFolioReferencia || false;
  }
}

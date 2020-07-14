import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IFolio, Folio } from 'app/shared/model/folio.model';
import { FolioService } from './folio.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ILibro } from 'app/shared/model/libro.model';
import { LibroService } from 'app/entities/libro/libro.service';
import { ITipoFolio } from 'app/shared/model/tipo-folio.model';
import { TipoFolioService } from 'app/entities/tipo-folio/tipo-folio.service';
import { IEstadoRespuesta } from 'app/shared/model/estado-respuesta.model';
import { EstadoRespuestaService } from 'app/entities/estado-respuesta/estado-respuesta.service';

type SelectableEntity = ILibro | ITipoFolio | IEstadoRespuesta;

@Component({
  selector: 'jhi-folio-update',
  templateUrl: './folio-update.component.html'
})
export class FolioUpdateComponent implements OnInit {
  isSaving = false;
  libros: ILibro[] = [];
  tipofolios: ITipoFolio[] = [];
  estadorespuestas: IEstadoRespuesta[] = [];

  editForm = this.fb.group({
    id: [],
    idUsuarioCreador: [],
    idUsuarioFirma: [],
    idUsuarioLectura: [],
    numeroFolio: [],
    requiereRespuesta: [],
    fechaRequerida: [],
    estadoLectura: [],
    estadoFolio: [],
    entidadCreacion: [],
    fechaCreacion: [],
    idlibroRelacionado: [],
    idFolioRelacionado: [],
    idFolioRespuesta: [],
    fechaModificacion: [],
    fechaFirma: [],
    fechaLectura: [],
    asunto: [null, [Validators.required, Validators.maxLength(80)]],
    anotacion: [],
    pdfFirmado: [],
    pdfFirmadoContentType: [],
    pdfLectura: [],
    pdfLecturaContentType: [],
    libro: [],
    tipoFolio: [],
    estadoRespuesta: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected folioService: FolioService,
    protected libroService: LibroService,
    protected tipoFolioService: TipoFolioService,
    protected estadoRespuestaService: EstadoRespuestaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ folio }) => {
      if (!folio.id) {
        const today = moment().startOf('day');
        folio.fechaRequerida = today;
        folio.fechaCreacion = today;
        folio.fechaModificacion = today;
        folio.fechaFirma = today;
        folio.fechaLectura = today;
      }

      this.updateForm(folio);

      this.libroService.query().subscribe((res: HttpResponse<ILibro[]>) => (this.libros = res.body || []));

      this.tipoFolioService.query().subscribe((res: HttpResponse<ITipoFolio[]>) => (this.tipofolios = res.body || []));

      this.estadoRespuestaService.query().subscribe((res: HttpResponse<IEstadoRespuesta[]>) => (this.estadorespuestas = res.body || []));
    });
  }

  updateForm(folio: IFolio): void {
    this.editForm.patchValue({
      id: folio.id,
      idUsuarioCreador: folio.idUsuarioCreador,
      idUsuarioFirma: folio.idUsuarioFirma,
      idUsuarioLectura: folio.idUsuarioLectura,
      numeroFolio: folio.numeroFolio,
      requiereRespuesta: folio.requiereRespuesta,
      fechaRequerida: folio.fechaRequerida ? folio.fechaRequerida.format(DATE_TIME_FORMAT) : null,
      estadoLectura: folio.estadoLectura,
      estadoFolio: folio.estadoFolio,
      entidadCreacion: folio.entidadCreacion,
      fechaCreacion: folio.fechaCreacion ? folio.fechaCreacion.format(DATE_TIME_FORMAT) : null,
      idlibroRelacionado: folio.idlibroRelacionado,
      idFolioRelacionado: folio.idFolioRelacionado,
      idFolioRespuesta: folio.idFolioRespuesta,
      fechaModificacion: folio.fechaModificacion ? folio.fechaModificacion.format(DATE_TIME_FORMAT) : null,
      fechaFirma: folio.fechaFirma ? folio.fechaFirma.format(DATE_TIME_FORMAT) : null,
      fechaLectura: folio.fechaLectura ? folio.fechaLectura.format(DATE_TIME_FORMAT) : null,
      asunto: folio.asunto,
      anotacion: folio.anotacion,
      pdfFirmado: folio.pdfFirmado,
      pdfFirmadoContentType: folio.pdfFirmadoContentType,
      pdfLectura: folio.pdfLectura,
      pdfLecturaContentType: folio.pdfLecturaContentType,
      libro: folio.libro,
      tipoFolio: folio.tipoFolio,
      estadoRespuesta: folio.estadoRespuesta
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('backendApp.error', { message: err.message })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const folio = this.createFromForm();
    if (folio.id !== undefined) {
      this.subscribeToSaveResponse(this.folioService.update(folio));
    } else {
      this.subscribeToSaveResponse(this.folioService.create(folio));
    }
  }

  private createFromForm(): IFolio {
    return {
      ...new Folio(),
      id: this.editForm.get(['id'])!.value,
      idUsuarioCreador: this.editForm.get(['idUsuarioCreador'])!.value,
      idUsuarioFirma: this.editForm.get(['idUsuarioFirma'])!.value,
      idUsuarioLectura: this.editForm.get(['idUsuarioLectura'])!.value,
      numeroFolio: this.editForm.get(['numeroFolio'])!.value,
      requiereRespuesta: this.editForm.get(['requiereRespuesta'])!.value,
      fechaRequerida: this.editForm.get(['fechaRequerida'])!.value
        ? moment(this.editForm.get(['fechaRequerida'])!.value, DATE_TIME_FORMAT)
        : undefined,
      estadoLectura: this.editForm.get(['estadoLectura'])!.value,
      estadoFolio: this.editForm.get(['estadoFolio'])!.value,
      entidadCreacion: this.editForm.get(['entidadCreacion'])!.value,
      fechaCreacion: this.editForm.get(['fechaCreacion'])!.value
        ? moment(this.editForm.get(['fechaCreacion'])!.value, DATE_TIME_FORMAT)
        : undefined,
      idlibroRelacionado: this.editForm.get(['idlibroRelacionado'])!.value,
      idFolioRelacionado: this.editForm.get(['idFolioRelacionado'])!.value,
      idFolioRespuesta: this.editForm.get(['idFolioRespuesta'])!.value,
      fechaModificacion: this.editForm.get(['fechaModificacion'])!.value
        ? moment(this.editForm.get(['fechaModificacion'])!.value, DATE_TIME_FORMAT)
        : undefined,
      fechaFirma: this.editForm.get(['fechaFirma'])!.value ? moment(this.editForm.get(['fechaFirma'])!.value, DATE_TIME_FORMAT) : undefined,
      fechaLectura: this.editForm.get(['fechaLectura'])!.value
        ? moment(this.editForm.get(['fechaLectura'])!.value, DATE_TIME_FORMAT)
        : undefined,
      asunto: this.editForm.get(['asunto'])!.value,
      anotacion: this.editForm.get(['anotacion'])!.value,
      pdfFirmadoContentType: this.editForm.get(['pdfFirmadoContentType'])!.value,
      pdfFirmado: this.editForm.get(['pdfFirmado'])!.value,
      pdfLecturaContentType: this.editForm.get(['pdfLecturaContentType'])!.value,
      pdfLectura: this.editForm.get(['pdfLectura'])!.value,
      libro: this.editForm.get(['libro'])!.value,
      tipoFolio: this.editForm.get(['tipoFolio'])!.value,
      estadoRespuesta: this.editForm.get(['estadoRespuesta'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFolio>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}

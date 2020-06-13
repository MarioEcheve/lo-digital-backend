import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ILibro, Libro } from 'app/shared/model/libro.model';
import { LibroService } from './libro.service';
import { IContrato } from 'app/shared/model/contrato.model';
import { ContratoService } from 'app/entities/contrato/contrato.service';
import { ITipoLibro } from 'app/shared/model/tipo-libro.model';
import { TipoLibroService } from 'app/entities/tipo-libro/tipo-libro.service';
import { ITipoFirma } from 'app/shared/model/tipo-firma.model';
import { TipoFirmaService } from 'app/entities/tipo-firma/tipo-firma.service';
import { IEstadoLibro } from 'app/shared/model/estado-libro.model';
import { EstadoLibroService } from 'app/entities/estado-libro/estado-libro.service';

type SelectableEntity = IContrato | ITipoLibro | ITipoFirma | IEstadoLibro;

@Component({
  selector: 'jhi-libro-update',
  templateUrl: './libro-update.component.html'
})
export class LibroUpdateComponent implements OnInit {
  isSaving = false;
  contratoes: IContrato[] = [];
  tipolibros: ITipoLibro[] = [];
  tipofirmas: ITipoFirma[] = [];
  estadolibros: IEstadoLibro[] = [];

  editForm = this.fb.group({
    id: [],
    codigo: [null, [Validators.maxLength(20)]],
    nombre: [null, [Validators.maxLength(60)]],
    descripcion: [null, [Validators.maxLength(250)]],
    fechaCreacion: [],
    fechaApertura: [],
    fechaCierre: [],
    aperturaMandante: [],
    aperturaContratista: [],
    escrituraMandante: [],
    escrituraContratista: [],
    cierreMandante: [],
    cierreContratista: [],
    lecturaMandante: [],
    lecturaContratista: [],
    contrato: [],
    tipoLibro: [],
    tipoFirma: [],
    estadoLibro: []
  });

  constructor(
    protected libroService: LibroService,
    protected contratoService: ContratoService,
    protected tipoLibroService: TipoLibroService,
    protected tipoFirmaService: TipoFirmaService,
    protected estadoLibroService: EstadoLibroService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ libro }) => {
      if (!libro.id) {
        const today = moment().startOf('day');
        libro.fechaCreacion = today;
        libro.fechaApertura = today;
        libro.fechaCierre = today;
      }

      this.updateForm(libro);

      this.contratoService.query().subscribe((res: HttpResponse<IContrato[]>) => (this.contratoes = res.body || []));

      this.tipoLibroService.query().subscribe((res: HttpResponse<ITipoLibro[]>) => (this.tipolibros = res.body || []));

      this.tipoFirmaService.query().subscribe((res: HttpResponse<ITipoFirma[]>) => (this.tipofirmas = res.body || []));

      this.estadoLibroService.query().subscribe((res: HttpResponse<IEstadoLibro[]>) => (this.estadolibros = res.body || []));
    });
  }

  updateForm(libro: ILibro): void {
    this.editForm.patchValue({
      id: libro.id,
      codigo: libro.codigo,
      nombre: libro.nombre,
      descripcion: libro.descripcion,
      fechaCreacion: libro.fechaCreacion ? libro.fechaCreacion.format(DATE_TIME_FORMAT) : null,
      fechaApertura: libro.fechaApertura ? libro.fechaApertura.format(DATE_TIME_FORMAT) : null,
      fechaCierre: libro.fechaCierre ? libro.fechaCierre.format(DATE_TIME_FORMAT) : null,
      aperturaMandante: libro.aperturaMandante,
      aperturaContratista: libro.aperturaContratista,
      escrituraMandante: libro.escrituraMandante,
      escrituraContratista: libro.escrituraContratista,
      cierreMandante: libro.cierreMandante,
      cierreContratista: libro.cierreContratista,
      lecturaMandante: libro.lecturaMandante,
      lecturaContratista: libro.lecturaContratista,
      contrato: libro.contrato,
      tipoLibro: libro.tipoLibro,
      tipoFirma: libro.tipoFirma,
      estadoLibro: libro.estadoLibro
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const libro = this.createFromForm();
    if (libro.id !== undefined) {
      this.subscribeToSaveResponse(this.libroService.update(libro));
    } else {
      this.subscribeToSaveResponse(this.libroService.create(libro));
    }
  }

  private createFromForm(): ILibro {
    return {
      ...new Libro(),
      id: this.editForm.get(['id'])!.value,
      codigo: this.editForm.get(['codigo'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      descripcion: this.editForm.get(['descripcion'])!.value,
      fechaCreacion: this.editForm.get(['fechaCreacion'])!.value
        ? moment(this.editForm.get(['fechaCreacion'])!.value, DATE_TIME_FORMAT)
        : undefined,
      fechaApertura: this.editForm.get(['fechaApertura'])!.value
        ? moment(this.editForm.get(['fechaApertura'])!.value, DATE_TIME_FORMAT)
        : undefined,
      fechaCierre: this.editForm.get(['fechaCierre'])!.value
        ? moment(this.editForm.get(['fechaCierre'])!.value, DATE_TIME_FORMAT)
        : undefined,
      aperturaMandante: this.editForm.get(['aperturaMandante'])!.value,
      aperturaContratista: this.editForm.get(['aperturaContratista'])!.value,
      escrituraMandante: this.editForm.get(['escrituraMandante'])!.value,
      escrituraContratista: this.editForm.get(['escrituraContratista'])!.value,
      cierreMandante: this.editForm.get(['cierreMandante'])!.value,
      cierreContratista: this.editForm.get(['cierreContratista'])!.value,
      lecturaMandante: this.editForm.get(['lecturaMandante'])!.value,
      lecturaContratista: this.editForm.get(['lecturaContratista'])!.value,
      contrato: this.editForm.get(['contrato'])!.value,
      tipoLibro: this.editForm.get(['tipoLibro'])!.value,
      tipoFirma: this.editForm.get(['tipoFirma'])!.value,
      estadoLibro: this.editForm.get(['estadoLibro'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILibro>>): void {
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

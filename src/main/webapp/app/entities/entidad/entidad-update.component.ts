import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IEntidad, Entidad } from 'app/shared/model/entidad.model';
import { EntidadService } from './entidad.service';
import { IRegion } from 'app/shared/model/region.model';
import { RegionService } from 'app/entities/region/region.service';
import { ITipoEntidad } from 'app/shared/model/tipo-entidad.model';
import { TipoEntidadService } from 'app/entities/tipo-entidad/tipo-entidad.service';
import { IActividadRubro } from 'app/shared/model/actividad-rubro.model';
import { ActividadRubroService } from 'app/entities/actividad-rubro/actividad-rubro.service';
import { IComuna } from 'app/shared/model/comuna.model';
import { ComunaService } from 'app/entities/comuna/comuna.service';

type SelectableEntity = IRegion | ITipoEntidad | IActividadRubro | IComuna;

@Component({
  selector: 'jhi-entidad-update',
  templateUrl: './entidad-update.component.html'
})
export class EntidadUpdateComponent implements OnInit {
  isSaving = false;
  regions: IRegion[] = [];
  tipoentidads: ITipoEntidad[] = [];
  actividadrubros: IActividadRubro[] = [];
  comunas: IComuna[] = [];

  editForm = this.fb.group({
    id: [],
    rut: [null, [Validators.required, Validators.maxLength(15)]],
    nombre: [null, [Validators.required, Validators.maxLength(100)]],
    direccion: [null, [Validators.maxLength(100)]],
    fechaCreacion: [],
    fechaModificacion: [],
    region: [],
    tipoEntidad: [],
    actividadRubro: [],
    comuna: []
  });

  constructor(
    protected entidadService: EntidadService,
    protected regionService: RegionService,
    protected tipoEntidadService: TipoEntidadService,
    protected actividadRubroService: ActividadRubroService,
    protected comunaService: ComunaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ entidad }) => {
      if (!entidad.id) {
        const today = moment().startOf('day');
        entidad.fechaCreacion = today;
        entidad.fechaModificacion = today;
      }

      this.updateForm(entidad);

      this.regionService.query().subscribe((res: HttpResponse<IRegion[]>) => (this.regions = res.body || []));

      this.tipoEntidadService.query().subscribe((res: HttpResponse<ITipoEntidad[]>) => (this.tipoentidads = res.body || []));

      this.actividadRubroService.query().subscribe((res: HttpResponse<IActividadRubro[]>) => (this.actividadrubros = res.body || []));

      this.comunaService.query().subscribe((res: HttpResponse<IComuna[]>) => (this.comunas = res.body || []));
    });
  }

  updateForm(entidad: IEntidad): void {
    this.editForm.patchValue({
      id: entidad.id,
      rut: entidad.rut,
      nombre: entidad.nombre,
      direccion: entidad.direccion,
      fechaCreacion: entidad.fechaCreacion ? entidad.fechaCreacion.format(DATE_TIME_FORMAT) : null,
      fechaModificacion: entidad.fechaModificacion ? entidad.fechaModificacion.format(DATE_TIME_FORMAT) : null,
      region: entidad.region,
      tipoEntidad: entidad.tipoEntidad,
      actividadRubro: entidad.actividadRubro,
      comuna: entidad.comuna
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const entidad = this.createFromForm();
    if (entidad.id !== undefined) {
      this.subscribeToSaveResponse(this.entidadService.update(entidad));
    } else {
      this.subscribeToSaveResponse(this.entidadService.create(entidad));
    }
  }

  private createFromForm(): IEntidad {
    return {
      ...new Entidad(),
      id: this.editForm.get(['id'])!.value,
      rut: this.editForm.get(['rut'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      direccion: this.editForm.get(['direccion'])!.value,
      fechaCreacion: this.editForm.get(['fechaCreacion'])!.value
        ? moment(this.editForm.get(['fechaCreacion'])!.value, DATE_TIME_FORMAT)
        : undefined,
      fechaModificacion: this.editForm.get(['fechaModificacion'])!.value
        ? moment(this.editForm.get(['fechaModificacion'])!.value, DATE_TIME_FORMAT)
        : undefined,
      region: this.editForm.get(['region'])!.value,
      tipoEntidad: this.editForm.get(['tipoEntidad'])!.value,
      actividadRubro: this.editForm.get(['actividadRubro'])!.value,
      comuna: this.editForm.get(['comuna'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEntidad>>): void {
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

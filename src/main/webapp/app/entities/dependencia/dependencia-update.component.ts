import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IDependencia, Dependencia } from 'app/shared/model/dependencia.model';
import { DependenciaService } from './dependencia.service';
import { IEntidad } from 'app/shared/model/entidad.model';
import { EntidadService } from 'app/entities/entidad/entidad.service';
import { IRegion } from 'app/shared/model/region.model';
import { RegionService } from 'app/entities/region/region.service';
import { IComuna } from 'app/shared/model/comuna.model';
import { ComunaService } from 'app/entities/comuna/comuna.service';

type SelectableEntity = IEntidad | IRegion | IComuna;

@Component({
  selector: 'jhi-dependencia-update',
  templateUrl: './dependencia-update.component.html'
})
export class DependenciaUpdateComponent implements OnInit {
  isSaving = false;
  entidads: IEntidad[] = [];
  regions: IRegion[] = [];
  comunas: IComuna[] = [];

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.maxLength(100)]],
    direccion: [null, [Validators.maxLength(110)]],
    descripcion: [null, [Validators.maxLength(150)]],
    fechaCreacion: [],
    fechaModificacion: [],
    entidad: [],
    region: [],
    comuna: []
  });

  constructor(
    protected dependenciaService: DependenciaService,
    protected entidadService: EntidadService,
    protected regionService: RegionService,
    protected comunaService: ComunaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dependencia }) => {
      if (!dependencia.id) {
        const today = moment().startOf('day');
        dependencia.fechaCreacion = today;
        dependencia.fechaModificacion = today;
      }

      this.updateForm(dependencia);

      this.entidadService.query().subscribe((res: HttpResponse<IEntidad[]>) => (this.entidads = res.body || []));

      this.regionService.query().subscribe((res: HttpResponse<IRegion[]>) => (this.regions = res.body || []));

      this.comunaService.query().subscribe((res: HttpResponse<IComuna[]>) => (this.comunas = res.body || []));
    });
  }

  updateForm(dependencia: IDependencia): void {
    this.editForm.patchValue({
      id: dependencia.id,
      nombre: dependencia.nombre,
      direccion: dependencia.direccion,
      descripcion: dependencia.descripcion,
      fechaCreacion: dependencia.fechaCreacion ? dependencia.fechaCreacion.format(DATE_TIME_FORMAT) : null,
      fechaModificacion: dependencia.fechaModificacion ? dependencia.fechaModificacion.format(DATE_TIME_FORMAT) : null,
      entidad: dependencia.entidad,
      region: dependencia.region,
      comuna: dependencia.comuna
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dependencia = this.createFromForm();
    if (dependencia.id !== undefined) {
      this.subscribeToSaveResponse(this.dependenciaService.update(dependencia));
    } else {
      this.subscribeToSaveResponse(this.dependenciaService.create(dependencia));
    }
  }

  private createFromForm(): IDependencia {
    return {
      ...new Dependencia(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      direccion: this.editForm.get(['direccion'])!.value,
      descripcion: this.editForm.get(['descripcion'])!.value,
      fechaCreacion: this.editForm.get(['fechaCreacion'])!.value
        ? moment(this.editForm.get(['fechaCreacion'])!.value, DATE_TIME_FORMAT)
        : undefined,
      fechaModificacion: this.editForm.get(['fechaModificacion'])!.value
        ? moment(this.editForm.get(['fechaModificacion'])!.value, DATE_TIME_FORMAT)
        : undefined,
      entidad: this.editForm.get(['entidad'])!.value,
      region: this.editForm.get(['region'])!.value,
      comuna: this.editForm.get(['comuna'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDependencia>>): void {
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

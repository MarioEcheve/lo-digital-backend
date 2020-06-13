import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITipoEntidad, TipoEntidad } from 'app/shared/model/tipo-entidad.model';
import { TipoEntidadService } from './tipo-entidad.service';

@Component({
  selector: 'jhi-tipo-entidad-update',
  templateUrl: './tipo-entidad-update.component.html'
})
export class TipoEntidadUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]]
  });

  constructor(protected tipoEntidadService: TipoEntidadService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoEntidad }) => {
      this.updateForm(tipoEntidad);
    });
  }

  updateForm(tipoEntidad: ITipoEntidad): void {
    this.editForm.patchValue({
      id: tipoEntidad.id,
      nombre: tipoEntidad.nombre
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tipoEntidad = this.createFromForm();
    if (tipoEntidad.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoEntidadService.update(tipoEntidad));
    } else {
      this.subscribeToSaveResponse(this.tipoEntidadService.create(tipoEntidad));
    }
  }

  private createFromForm(): ITipoEntidad {
    return {
      ...new TipoEntidad(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoEntidad>>): void {
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
}

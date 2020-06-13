import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITipoMoneda, TipoMoneda } from 'app/shared/model/tipo-moneda.model';
import { TipoMonedaService } from './tipo-moneda.service';

@Component({
  selector: 'jhi-tipo-moneda-update',
  templateUrl: './tipo-moneda-update.component.html'
})
export class TipoMonedaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: []
  });

  constructor(protected tipoMonedaService: TipoMonedaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoMoneda }) => {
      this.updateForm(tipoMoneda);
    });
  }

  updateForm(tipoMoneda: ITipoMoneda): void {
    this.editForm.patchValue({
      id: tipoMoneda.id,
      nombre: tipoMoneda.nombre
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tipoMoneda = this.createFromForm();
    if (tipoMoneda.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoMonedaService.update(tipoMoneda));
    } else {
      this.subscribeToSaveResponse(this.tipoMonedaService.create(tipoMoneda));
    }
  }

  private createFromForm(): ITipoMoneda {
    return {
      ...new TipoMoneda(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoMoneda>>): void {
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

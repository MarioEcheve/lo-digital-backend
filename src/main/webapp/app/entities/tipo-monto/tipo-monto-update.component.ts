import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITipoMonto, TipoMonto } from 'app/shared/model/tipo-monto.model';
import { TipoMontoService } from './tipo-monto.service';

@Component({
  selector: 'jhi-tipo-monto-update',
  templateUrl: './tipo-monto-update.component.html'
})
export class TipoMontoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: []
  });

  constructor(protected tipoMontoService: TipoMontoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoMonto }) => {
      this.updateForm(tipoMonto);
    });
  }

  updateForm(tipoMonto: ITipoMonto): void {
    this.editForm.patchValue({
      id: tipoMonto.id,
      nombre: tipoMonto.nombre
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tipoMonto = this.createFromForm();
    if (tipoMonto.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoMontoService.update(tipoMonto));
    } else {
      this.subscribeToSaveResponse(this.tipoMontoService.create(tipoMonto));
    }
  }

  private createFromForm(): ITipoMonto {
    return {
      ...new TipoMonto(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoMonto>>): void {
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

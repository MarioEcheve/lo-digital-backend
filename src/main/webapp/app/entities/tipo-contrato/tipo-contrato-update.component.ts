import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITipoContrato, TipoContrato } from 'app/shared/model/tipo-contrato.model';
import { TipoContratoService } from './tipo-contrato.service';

@Component({
  selector: 'jhi-tipo-contrato-update',
  templateUrl: './tipo-contrato-update.component.html'
})
export class TipoContratoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]]
  });

  constructor(protected tipoContratoService: TipoContratoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoContrato }) => {
      this.updateForm(tipoContrato);
    });
  }

  updateForm(tipoContrato: ITipoContrato): void {
    this.editForm.patchValue({
      id: tipoContrato.id,
      nombre: tipoContrato.nombre
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tipoContrato = this.createFromForm();
    if (tipoContrato.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoContratoService.update(tipoContrato));
    } else {
      this.subscribeToSaveResponse(this.tipoContratoService.create(tipoContrato));
    }
  }

  private createFromForm(): ITipoContrato {
    return {
      ...new TipoContrato(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoContrato>>): void {
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

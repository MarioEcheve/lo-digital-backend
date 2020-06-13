import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITipoFolio, TipoFolio } from 'app/shared/model/tipo-folio.model';
import { TipoFolioService } from './tipo-folio.service';

@Component({
  selector: 'jhi-tipo-folio-update',
  templateUrl: './tipo-folio-update.component.html'
})
export class TipoFolioUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.maxLength(20)]],
    visibleMaestro: [null, [Validators.required]],
    visibleAuxliar: [null, [Validators.required]],
    visibleMandante: [null, [Validators.required]],
    visibleContratista: [null, [Validators.required]]
  });

  constructor(protected tipoFolioService: TipoFolioService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoFolio }) => {
      this.updateForm(tipoFolio);
    });
  }

  updateForm(tipoFolio: ITipoFolio): void {
    this.editForm.patchValue({
      id: tipoFolio.id,
      nombre: tipoFolio.nombre,
      visibleMaestro: tipoFolio.visibleMaestro,
      visibleAuxliar: tipoFolio.visibleAuxliar,
      visibleMandante: tipoFolio.visibleMandante,
      visibleContratista: tipoFolio.visibleContratista
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tipoFolio = this.createFromForm();
    if (tipoFolio.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoFolioService.update(tipoFolio));
    } else {
      this.subscribeToSaveResponse(this.tipoFolioService.create(tipoFolio));
    }
  }

  private createFromForm(): ITipoFolio {
    return {
      ...new TipoFolio(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      visibleMaestro: this.editForm.get(['visibleMaestro'])!.value,
      visibleAuxliar: this.editForm.get(['visibleAuxliar'])!.value,
      visibleMandante: this.editForm.get(['visibleMandante'])!.value,
      visibleContratista: this.editForm.get(['visibleContratista'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoFolio>>): void {
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

import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITipoFirma, TipoFirma } from 'app/shared/model/tipo-firma.model';
import { TipoFirmaService } from './tipo-firma.service';

@Component({
  selector: 'jhi-tipo-firma-update',
  templateUrl: './tipo-firma-update.component.html'
})
export class TipoFirmaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: []
  });

  constructor(protected tipoFirmaService: TipoFirmaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoFirma }) => {
      this.updateForm(tipoFirma);
    });
  }

  updateForm(tipoFirma: ITipoFirma): void {
    this.editForm.patchValue({
      id: tipoFirma.id,
      nombre: tipoFirma.nombre
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tipoFirma = this.createFromForm();
    if (tipoFirma.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoFirmaService.update(tipoFirma));
    } else {
      this.subscribeToSaveResponse(this.tipoFirmaService.create(tipoFirma));
    }
  }

  private createFromForm(): ITipoFirma {
    return {
      ...new TipoFirma(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoFirma>>): void {
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

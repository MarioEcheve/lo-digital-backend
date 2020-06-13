import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITipoUsuarioDependencia, TipoUsuarioDependencia } from 'app/shared/model/tipo-usuario-dependencia.model';
import { TipoUsuarioDependenciaService } from './tipo-usuario-dependencia.service';

@Component({
  selector: 'jhi-tipo-usuario-dependencia-update',
  templateUrl: './tipo-usuario-dependencia-update.component.html'
})
export class TipoUsuarioDependenciaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]]
  });

  constructor(
    protected tipoUsuarioDependenciaService: TipoUsuarioDependenciaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoUsuarioDependencia }) => {
      this.updateForm(tipoUsuarioDependencia);
    });
  }

  updateForm(tipoUsuarioDependencia: ITipoUsuarioDependencia): void {
    this.editForm.patchValue({
      id: tipoUsuarioDependencia.id,
      nombre: tipoUsuarioDependencia.nombre
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tipoUsuarioDependencia = this.createFromForm();
    if (tipoUsuarioDependencia.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoUsuarioDependenciaService.update(tipoUsuarioDependencia));
    } else {
      this.subscribeToSaveResponse(this.tipoUsuarioDependenciaService.create(tipoUsuarioDependencia));
    }
  }

  private createFromForm(): ITipoUsuarioDependencia {
    return {
      ...new TipoUsuarioDependencia(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoUsuarioDependencia>>): void {
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

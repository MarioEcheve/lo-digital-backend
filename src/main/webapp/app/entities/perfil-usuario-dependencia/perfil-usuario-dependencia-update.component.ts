import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPerfilUsuarioDependencia, PerfilUsuarioDependencia } from 'app/shared/model/perfil-usuario-dependencia.model';
import { PerfilUsuarioDependenciaService } from './perfil-usuario-dependencia.service';

@Component({
  selector: 'jhi-perfil-usuario-dependencia-update',
  templateUrl: './perfil-usuario-dependencia-update.component.html'
})
export class PerfilUsuarioDependenciaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: []
  });

  constructor(
    protected perfilUsuarioDependenciaService: PerfilUsuarioDependenciaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ perfilUsuarioDependencia }) => {
      this.updateForm(perfilUsuarioDependencia);
    });
  }

  updateForm(perfilUsuarioDependencia: IPerfilUsuarioDependencia): void {
    this.editForm.patchValue({
      id: perfilUsuarioDependencia.id,
      nombre: perfilUsuarioDependencia.nombre
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const perfilUsuarioDependencia = this.createFromForm();
    if (perfilUsuarioDependencia.id !== undefined) {
      this.subscribeToSaveResponse(this.perfilUsuarioDependenciaService.update(perfilUsuarioDependencia));
    } else {
      this.subscribeToSaveResponse(this.perfilUsuarioDependenciaService.create(perfilUsuarioDependencia));
    }
  }

  private createFromForm(): IPerfilUsuarioDependencia {
    return {
      ...new PerfilUsuarioDependencia(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPerfilUsuarioDependencia>>): void {
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

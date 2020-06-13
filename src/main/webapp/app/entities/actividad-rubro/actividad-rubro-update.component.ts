import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IActividadRubro, ActividadRubro } from 'app/shared/model/actividad-rubro.model';
import { ActividadRubroService } from './actividad-rubro.service';

@Component({
  selector: 'jhi-actividad-rubro-update',
  templateUrl: './actividad-rubro-update.component.html'
})
export class ActividadRubroUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: []
  });

  constructor(protected actividadRubroService: ActividadRubroService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ actividadRubro }) => {
      this.updateForm(actividadRubro);
    });
  }

  updateForm(actividadRubro: IActividadRubro): void {
    this.editForm.patchValue({
      id: actividadRubro.id,
      nombre: actividadRubro.nombre
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const actividadRubro = this.createFromForm();
    if (actividadRubro.id !== undefined) {
      this.subscribeToSaveResponse(this.actividadRubroService.update(actividadRubro));
    } else {
      this.subscribeToSaveResponse(this.actividadRubroService.create(actividadRubro));
    }
  }

  private createFromForm(): IActividadRubro {
    return {
      ...new ActividadRubro(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IActividadRubro>>): void {
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

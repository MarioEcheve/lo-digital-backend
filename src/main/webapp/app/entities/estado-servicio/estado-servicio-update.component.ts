import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEstadoServicio, EstadoServicio } from 'app/shared/model/estado-servicio.model';
import { EstadoServicioService } from './estado-servicio.service';

@Component({
  selector: 'jhi-estado-servicio-update',
  templateUrl: './estado-servicio-update.component.html'
})
export class EstadoServicioUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: []
  });

  constructor(protected estadoServicioService: EstadoServicioService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ estadoServicio }) => {
      this.updateForm(estadoServicio);
    });
  }

  updateForm(estadoServicio: IEstadoServicio): void {
    this.editForm.patchValue({
      id: estadoServicio.id,
      nombre: estadoServicio.nombre
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const estadoServicio = this.createFromForm();
    if (estadoServicio.id !== undefined) {
      this.subscribeToSaveResponse(this.estadoServicioService.update(estadoServicio));
    } else {
      this.subscribeToSaveResponse(this.estadoServicioService.create(estadoServicio));
    }
  }

  private createFromForm(): IEstadoServicio {
    return {
      ...new EstadoServicio(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstadoServicio>>): void {
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

import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEstadoRespuesta, EstadoRespuesta } from 'app/shared/model/estado-respuesta.model';
import { EstadoRespuestaService } from './estado-respuesta.service';

@Component({
  selector: 'jhi-estado-respuesta-update',
  templateUrl: './estado-respuesta-update.component.html'
})
export class EstadoRespuestaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: []
  });

  constructor(
    protected estadoRespuestaService: EstadoRespuestaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ estadoRespuesta }) => {
      this.updateForm(estadoRespuesta);
    });
  }

  updateForm(estadoRespuesta: IEstadoRespuesta): void {
    this.editForm.patchValue({
      id: estadoRespuesta.id,
      nombre: estadoRespuesta.nombre
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const estadoRespuesta = this.createFromForm();
    if (estadoRespuesta.id !== undefined) {
      this.subscribeToSaveResponse(this.estadoRespuestaService.update(estadoRespuesta));
    } else {
      this.subscribeToSaveResponse(this.estadoRespuestaService.create(estadoRespuesta));
    }
  }

  private createFromForm(): IEstadoRespuesta {
    return {
      ...new EstadoRespuesta(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstadoRespuesta>>): void {
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

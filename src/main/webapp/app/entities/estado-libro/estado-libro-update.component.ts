import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEstadoLibro, EstadoLibro } from 'app/shared/model/estado-libro.model';
import { EstadoLibroService } from './estado-libro.service';

@Component({
  selector: 'jhi-estado-libro-update',
  templateUrl: './estado-libro-update.component.html'
})
export class EstadoLibroUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: []
  });

  constructor(protected estadoLibroService: EstadoLibroService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ estadoLibro }) => {
      this.updateForm(estadoLibro);
    });
  }

  updateForm(estadoLibro: IEstadoLibro): void {
    this.editForm.patchValue({
      id: estadoLibro.id,
      nombre: estadoLibro.nombre
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const estadoLibro = this.createFromForm();
    if (estadoLibro.id !== undefined) {
      this.subscribeToSaveResponse(this.estadoLibroService.update(estadoLibro));
    } else {
      this.subscribeToSaveResponse(this.estadoLibroService.create(estadoLibro));
    }
  }

  private createFromForm(): IEstadoLibro {
    return {
      ...new EstadoLibro(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstadoLibro>>): void {
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

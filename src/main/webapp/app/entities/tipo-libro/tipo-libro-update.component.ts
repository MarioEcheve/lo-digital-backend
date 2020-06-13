import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITipoLibro, TipoLibro } from 'app/shared/model/tipo-libro.model';
import { TipoLibroService } from './tipo-libro.service';

@Component({
  selector: 'jhi-tipo-libro-update',
  templateUrl: './tipo-libro-update.component.html'
})
export class TipoLibroUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    descripcion: []
  });

  constructor(protected tipoLibroService: TipoLibroService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoLibro }) => {
      this.updateForm(tipoLibro);
    });
  }

  updateForm(tipoLibro: ITipoLibro): void {
    this.editForm.patchValue({
      id: tipoLibro.id,
      descripcion: tipoLibro.descripcion
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tipoLibro = this.createFromForm();
    if (tipoLibro.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoLibroService.update(tipoLibro));
    } else {
      this.subscribeToSaveResponse(this.tipoLibroService.create(tipoLibro));
    }
  }

  private createFromForm(): ITipoLibro {
    return {
      ...new TipoLibro(),
      id: this.editForm.get(['id'])!.value,
      descripcion: this.editForm.get(['descripcion'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoLibro>>): void {
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

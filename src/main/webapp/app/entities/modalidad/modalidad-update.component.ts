import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IModalidad, Modalidad } from 'app/shared/model/modalidad.model';
import { ModalidadService } from './modalidad.service';

@Component({
  selector: 'jhi-modalidad-update',
  templateUrl: './modalidad-update.component.html'
})
export class ModalidadUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]]
  });

  constructor(protected modalidadService: ModalidadService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ modalidad }) => {
      this.updateForm(modalidad);
    });
  }

  updateForm(modalidad: IModalidad): void {
    this.editForm.patchValue({
      id: modalidad.id,
      nombre: modalidad.nombre
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const modalidad = this.createFromForm();
    if (modalidad.id !== undefined) {
      this.subscribeToSaveResponse(this.modalidadService.update(modalidad));
    } else {
      this.subscribeToSaveResponse(this.modalidadService.create(modalidad));
    }
  }

  private createFromForm(): IModalidad {
    return {
      ...new Modalidad(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IModalidad>>): void {
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

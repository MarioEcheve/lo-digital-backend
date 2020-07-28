import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFolioReferencia, FolioReferencia } from 'app/shared/model/folio-referencia.model';
import { FolioReferenciaService } from './folio-referencia.service';

@Component({
  selector: 'jhi-folio-referencia-update',
  templateUrl: './folio-referencia-update.component.html'
})
export class FolioReferenciaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    asunto: [],
    idFolioOrigen: [],
    idFolioReferencia: []
  });

  constructor(
    protected folioReferenciaService: FolioReferenciaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ folioReferencia }) => {
      this.updateForm(folioReferencia);
    });
  }

  updateForm(folioReferencia: IFolioReferencia): void {
    this.editForm.patchValue({
      id: folioReferencia.id,
      asunto: folioReferencia.asunto,
      idFolioOrigen: folioReferencia.idFolioOrigen,
      idFolioReferencia: folioReferencia.idFolioReferencia
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const folioReferencia = this.createFromForm();
    if (folioReferencia.id !== undefined) {
      this.subscribeToSaveResponse(this.folioReferenciaService.update(folioReferencia));
    } else {
      this.subscribeToSaveResponse(this.folioReferenciaService.create(folioReferencia));
    }
  }

  private createFromForm(): IFolioReferencia {
    return {
      ...new FolioReferencia(),
      id: this.editForm.get(['id'])!.value,
      asunto: this.editForm.get(['asunto'])!.value,
      idFolioOrigen: this.editForm.get(['idFolioOrigen'])!.value,
      idFolioReferencia: this.editForm.get(['idFolioReferencia'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFolioReferencia>>): void {
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

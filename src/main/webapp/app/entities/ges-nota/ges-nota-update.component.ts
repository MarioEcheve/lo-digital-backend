import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IGesNota, GesNota } from 'app/shared/model/ges-nota.model';
import { GesNotaService } from './ges-nota.service';
import { IFolio } from 'app/shared/model/folio.model';
import { FolioService } from 'app/entities/folio/folio.service';
import { IUsuarioLibro } from 'app/shared/model/usuario-libro.model';
import { UsuarioLibroService } from 'app/entities/usuario-libro/usuario-libro.service';

type SelectableEntity = IFolio | IUsuarioLibro;

@Component({
  selector: 'jhi-ges-nota-update',
  templateUrl: './ges-nota-update.component.html'
})
export class GesNotaUpdateComponent implements OnInit {
  isSaving = false;
  folios: IFolio[] = [];
  usuariolibros: IUsuarioLibro[] = [];

  editForm = this.fb.group({
    id: [],
    nota: [null, [Validators.required, Validators.maxLength(100)]],
    fechaCreacion: [],
    fechaModificacion: [],
    folio: [],
    usuarioLibro: []
  });

  constructor(
    protected gesNotaService: GesNotaService,
    protected folioService: FolioService,
    protected usuarioLibroService: UsuarioLibroService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gesNota }) => {
      if (!gesNota.id) {
        const today = moment().startOf('day');
        gesNota.fechaCreacion = today;
        gesNota.fechaModificacion = today;
      }

      this.updateForm(gesNota);

      this.folioService.query().subscribe((res: HttpResponse<IFolio[]>) => (this.folios = res.body || []));

      this.usuarioLibroService.query().subscribe((res: HttpResponse<IUsuarioLibro[]>) => (this.usuariolibros = res.body || []));
    });
  }

  updateForm(gesNota: IGesNota): void {
    this.editForm.patchValue({
      id: gesNota.id,
      nota: gesNota.nota,
      fechaCreacion: gesNota.fechaCreacion ? gesNota.fechaCreacion.format(DATE_TIME_FORMAT) : null,
      fechaModificacion: gesNota.fechaModificacion ? gesNota.fechaModificacion.format(DATE_TIME_FORMAT) : null,
      folio: gesNota.folio,
      usuarioLibro: gesNota.usuarioLibro
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const gesNota = this.createFromForm();
    if (gesNota.id !== undefined) {
      this.subscribeToSaveResponse(this.gesNotaService.update(gesNota));
    } else {
      this.subscribeToSaveResponse(this.gesNotaService.create(gesNota));
    }
  }

  private createFromForm(): IGesNota {
    return {
      ...new GesNota(),
      id: this.editForm.get(['id'])!.value,
      nota: this.editForm.get(['nota'])!.value,
      fechaCreacion: this.editForm.get(['fechaCreacion'])!.value
        ? moment(this.editForm.get(['fechaCreacion'])!.value, DATE_TIME_FORMAT)
        : undefined,
      fechaModificacion: this.editForm.get(['fechaModificacion'])!.value
        ? moment(this.editForm.get(['fechaModificacion'])!.value, DATE_TIME_FORMAT)
        : undefined,
      folio: this.editForm.get(['folio'])!.value,
      usuarioLibro: this.editForm.get(['usuarioLibro'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGesNota>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}

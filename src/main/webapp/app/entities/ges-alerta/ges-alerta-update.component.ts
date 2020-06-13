import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IGesAlerta, GesAlerta } from 'app/shared/model/ges-alerta.model';
import { GesAlertaService } from './ges-alerta.service';
import { IFolio } from 'app/shared/model/folio.model';
import { FolioService } from 'app/entities/folio/folio.service';
import { IUsuarioLibro } from 'app/shared/model/usuario-libro.model';
import { UsuarioLibroService } from 'app/entities/usuario-libro/usuario-libro.service';

type SelectableEntity = IFolio | IUsuarioLibro;

@Component({
  selector: 'jhi-ges-alerta-update',
  templateUrl: './ges-alerta-update.component.html'
})
export class GesAlertaUpdateComponent implements OnInit {
  isSaving = false;
  folios: IFolio[] = [];
  usuariolibros: IUsuarioLibro[] = [];

  editForm = this.fb.group({
    id: [],
    nota: [null, [Validators.required, Validators.maxLength(100)]],
    fechaAlerta: [null, [Validators.required]],
    fechaCreacion: [],
    fechaModificacion: [],
    folio: [],
    usuarioLibro: []
  });

  constructor(
    protected gesAlertaService: GesAlertaService,
    protected folioService: FolioService,
    protected usuarioLibroService: UsuarioLibroService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gesAlerta }) => {
      if (!gesAlerta.id) {
        const today = moment().startOf('day');
        gesAlerta.fechaAlerta = today;
        gesAlerta.fechaCreacion = today;
        gesAlerta.fechaModificacion = today;
      }

      this.updateForm(gesAlerta);

      this.folioService.query().subscribe((res: HttpResponse<IFolio[]>) => (this.folios = res.body || []));

      this.usuarioLibroService.query().subscribe((res: HttpResponse<IUsuarioLibro[]>) => (this.usuariolibros = res.body || []));
    });
  }

  updateForm(gesAlerta: IGesAlerta): void {
    this.editForm.patchValue({
      id: gesAlerta.id,
      nota: gesAlerta.nota,
      fechaAlerta: gesAlerta.fechaAlerta ? gesAlerta.fechaAlerta.format(DATE_TIME_FORMAT) : null,
      fechaCreacion: gesAlerta.fechaCreacion ? gesAlerta.fechaCreacion.format(DATE_TIME_FORMAT) : null,
      fechaModificacion: gesAlerta.fechaModificacion ? gesAlerta.fechaModificacion.format(DATE_TIME_FORMAT) : null,
      folio: gesAlerta.folio,
      usuarioLibro: gesAlerta.usuarioLibro
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const gesAlerta = this.createFromForm();
    if (gesAlerta.id !== undefined) {
      this.subscribeToSaveResponse(this.gesAlertaService.update(gesAlerta));
    } else {
      this.subscribeToSaveResponse(this.gesAlertaService.create(gesAlerta));
    }
  }

  private createFromForm(): IGesAlerta {
    return {
      ...new GesAlerta(),
      id: this.editForm.get(['id'])!.value,
      nota: this.editForm.get(['nota'])!.value,
      fechaAlerta: this.editForm.get(['fechaAlerta'])!.value
        ? moment(this.editForm.get(['fechaAlerta'])!.value, DATE_TIME_FORMAT)
        : undefined,
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGesAlerta>>): void {
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

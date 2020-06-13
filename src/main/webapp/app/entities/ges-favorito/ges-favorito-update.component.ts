import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IGesFavorito, GesFavorito } from 'app/shared/model/ges-favorito.model';
import { GesFavoritoService } from './ges-favorito.service';
import { IFolio } from 'app/shared/model/folio.model';
import { FolioService } from 'app/entities/folio/folio.service';
import { IUsuarioLibro } from 'app/shared/model/usuario-libro.model';
import { UsuarioLibroService } from 'app/entities/usuario-libro/usuario-libro.service';

type SelectableEntity = IFolio | IUsuarioLibro;

@Component({
  selector: 'jhi-ges-favorito-update',
  templateUrl: './ges-favorito-update.component.html'
})
export class GesFavoritoUpdateComponent implements OnInit {
  isSaving = false;
  folios: IFolio[] = [];
  usuariolibros: IUsuarioLibro[] = [];

  editForm = this.fb.group({
    id: [],
    nota: [null, [Validators.required, Validators.maxLength(100)]],
    fechaCreacion: [],
    fechaModificacion: [],
    favorito: [],
    folio: [],
    usuarioLibro: []
  });

  constructor(
    protected gesFavoritoService: GesFavoritoService,
    protected folioService: FolioService,
    protected usuarioLibroService: UsuarioLibroService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gesFavorito }) => {
      if (!gesFavorito.id) {
        const today = moment().startOf('day');
        gesFavorito.fechaCreacion = today;
        gesFavorito.fechaModificacion = today;
      }

      this.updateForm(gesFavorito);

      this.folioService.query().subscribe((res: HttpResponse<IFolio[]>) => (this.folios = res.body || []));

      this.usuarioLibroService.query().subscribe((res: HttpResponse<IUsuarioLibro[]>) => (this.usuariolibros = res.body || []));
    });
  }

  updateForm(gesFavorito: IGesFavorito): void {
    this.editForm.patchValue({
      id: gesFavorito.id,
      nota: gesFavorito.nota,
      fechaCreacion: gesFavorito.fechaCreacion ? gesFavorito.fechaCreacion.format(DATE_TIME_FORMAT) : null,
      fechaModificacion: gesFavorito.fechaModificacion ? gesFavorito.fechaModificacion.format(DATE_TIME_FORMAT) : null,
      favorito: gesFavorito.favorito,
      folio: gesFavorito.folio,
      usuarioLibro: gesFavorito.usuarioLibro
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const gesFavorito = this.createFromForm();
    if (gesFavorito.id !== undefined) {
      this.subscribeToSaveResponse(this.gesFavoritoService.update(gesFavorito));
    } else {
      this.subscribeToSaveResponse(this.gesFavoritoService.create(gesFavorito));
    }
  }

  private createFromForm(): IGesFavorito {
    return {
      ...new GesFavorito(),
      id: this.editForm.get(['id'])!.value,
      nota: this.editForm.get(['nota'])!.value,
      fechaCreacion: this.editForm.get(['fechaCreacion'])!.value
        ? moment(this.editForm.get(['fechaCreacion'])!.value, DATE_TIME_FORMAT)
        : undefined,
      fechaModificacion: this.editForm.get(['fechaModificacion'])!.value
        ? moment(this.editForm.get(['fechaModificacion'])!.value, DATE_TIME_FORMAT)
        : undefined,
      favorito: this.editForm.get(['favorito'])!.value,
      folio: this.editForm.get(['folio'])!.value,
      usuarioLibro: this.editForm.get(['usuarioLibro'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGesFavorito>>): void {
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

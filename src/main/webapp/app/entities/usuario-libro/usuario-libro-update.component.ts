import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IUsuarioLibro, UsuarioLibro } from 'app/shared/model/usuario-libro.model';
import { UsuarioLibroService } from './usuario-libro.service';
import { ILibro } from 'app/shared/model/libro.model';
import { LibroService } from 'app/entities/libro/libro.service';
import { IUsuarioDependencia } from 'app/shared/model/usuario-dependencia.model';
import { UsuarioDependenciaService } from 'app/entities/usuario-dependencia/usuario-dependencia.service';
import { IUsuarioLibroPerfil } from 'app/shared/model/usuario-libro-perfil.model';
import { UsuarioLibroPerfilService } from 'app/entities/usuario-libro-perfil/usuario-libro-perfil.service';

type SelectableEntity = ILibro | IUsuarioDependencia | IUsuarioLibroPerfil;

@Component({
  selector: 'jhi-usuario-libro-update',
  templateUrl: './usuario-libro-update.component.html'
})
export class UsuarioLibroUpdateComponent implements OnInit {
  isSaving = false;
  libros: ILibro[] = [];
  usuariodependencias: IUsuarioDependencia[] = [];
  usuariolibroperfils: IUsuarioLibroPerfil[] = [];

  editForm = this.fb.group({
    id: [],
    estado: [],
    cargoFuncion: [],
    fechaCreacion: [],
    fechaModificacion: [],
    adminActivo: [],
    libro: [],
    usuarioDependencia: [],
    perfilUsuarioLibro: []
  });

  constructor(
    protected usuarioLibroService: UsuarioLibroService,
    protected libroService: LibroService,
    protected usuarioDependenciaService: UsuarioDependenciaService,
    protected usuarioLibroPerfilService: UsuarioLibroPerfilService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ usuarioLibro }) => {
      if (!usuarioLibro.id) {
        const today = moment().startOf('day');
        usuarioLibro.fechaCreacion = today;
        usuarioLibro.fechaModificacion = today;
      }

      this.updateForm(usuarioLibro);

      this.libroService.query().subscribe((res: HttpResponse<ILibro[]>) => (this.libros = res.body || []));

      this.usuarioDependenciaService
        .query()
        .subscribe((res: HttpResponse<IUsuarioDependencia[]>) => (this.usuariodependencias = res.body || []));

      this.usuarioLibroPerfilService
        .query()
        .subscribe((res: HttpResponse<IUsuarioLibroPerfil[]>) => (this.usuariolibroperfils = res.body || []));
    });
  }

  updateForm(usuarioLibro: IUsuarioLibro): void {
    this.editForm.patchValue({
      id: usuarioLibro.id,
      estado: usuarioLibro.estado,
      cargoFuncion: usuarioLibro.cargoFuncion,
      fechaCreacion: usuarioLibro.fechaCreacion ? usuarioLibro.fechaCreacion.format(DATE_TIME_FORMAT) : null,
      fechaModificacion: usuarioLibro.fechaModificacion ? usuarioLibro.fechaModificacion.format(DATE_TIME_FORMAT) : null,
      adminActivo: usuarioLibro.adminActivo,
      libro: usuarioLibro.libro,
      usuarioDependencia: usuarioLibro.usuarioDependencia,
      perfilUsuarioLibro: usuarioLibro.perfilUsuarioLibro
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const usuarioLibro = this.createFromForm();
    if (usuarioLibro.id !== undefined) {
      this.subscribeToSaveResponse(this.usuarioLibroService.update(usuarioLibro));
    } else {
      this.subscribeToSaveResponse(this.usuarioLibroService.create(usuarioLibro));
    }
  }

  private createFromForm(): IUsuarioLibro {
    return {
      ...new UsuarioLibro(),
      id: this.editForm.get(['id'])!.value,
      estado: this.editForm.get(['estado'])!.value,
      cargoFuncion: this.editForm.get(['cargoFuncion'])!.value,
      fechaCreacion: this.editForm.get(['fechaCreacion'])!.value
        ? moment(this.editForm.get(['fechaCreacion'])!.value, DATE_TIME_FORMAT)
        : undefined,
      fechaModificacion: this.editForm.get(['fechaModificacion'])!.value
        ? moment(this.editForm.get(['fechaModificacion'])!.value, DATE_TIME_FORMAT)
        : undefined,
      adminActivo: this.editForm.get(['adminActivo'])!.value,
      libro: this.editForm.get(['libro'])!.value,
      usuarioDependencia: this.editForm.get(['usuarioDependencia'])!.value,
      perfilUsuarioLibro: this.editForm.get(['perfilUsuarioLibro'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUsuarioLibro>>): void {
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
